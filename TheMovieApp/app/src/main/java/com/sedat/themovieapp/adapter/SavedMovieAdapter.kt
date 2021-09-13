package com.sedat.themovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sedat.themovieapp.databinding.MovieListItemBinding
import com.sedat.themovieapp.model.SavedMovie
import javax.inject.Inject

class SavedMovieAdapter @Inject constructor(
        private val glide: RequestManager
): PagingDataAdapter<SavedMovie, SavedMovieAdapter.Holder>(diffUtil()) {

    class diffUtil: DiffUtil.ItemCallback<SavedMovie>(){
        override fun areItemsTheSame(oldItem: SavedMovie, newItem: SavedMovie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SavedMovie, newItem: SavedMovie): Boolean {
            return oldItem == newItem
        }

    }

    private var onItemClickListener: ((Int) -> Unit) ?= null
    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: SavedMovieAdapter.Holder, position: Int) {
        val movie = getItem(position)!!

        holder.item.apply {
            movieName.text = "--> ${movie.movie_title}"
            releaseDate.text = ": ${movie.movie_release_date}"
            voteAvarage.text = "IMDB: ${movie.movie_imdb}"
            glide.load("https://image.tmdb.org/t/p/w500${movie.movie_url}").into(movieImage)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(movie.movie_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMovieAdapter.Holder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    class Holder(val item: MovieListItemBinding): RecyclerView.ViewHolder(item.root)

}