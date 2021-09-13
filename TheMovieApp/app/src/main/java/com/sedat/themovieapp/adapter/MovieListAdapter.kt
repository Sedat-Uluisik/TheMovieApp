package com.sedat.themovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sedat.themovieapp.databinding.MovieListItemBinding
import com.sedat.themovieapp.model.Result
import javax.inject.Inject

class MovieListAdapter @Inject constructor(
    private val glide: RequestManager
): PagingDataAdapter<Result, MovieListAdapter.Holder>(diffutil()) {

    class diffutil: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    private var onItemClickListener: ((Int) -> Unit) ?= null
    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: MovieListAdapter.Holder, position: Int) {
        val movie = getItem(position)!!

        holder.item.apply {
            movieName.text = "--> ${movie.title}"
            releaseDate.text = ": ${movie.releaseDate}"
            voteAvarage.text = "IMDB: ${movie.voteAverage}"
            glide.load("https://image.tmdb.org/t/p/w500${movie.posterPath}").into(movieImage)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(movie.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.Holder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    class Holder(val item: MovieListItemBinding): RecyclerView.ViewHolder(item.root){

    }
}