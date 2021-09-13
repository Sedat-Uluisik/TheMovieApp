package com.sedat.themovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.themovieapp.R
import com.sedat.themovieapp.databinding.MovieImagesItemLayoutBinding
import javax.inject.Inject

class MovieImagesAdapter @Inject constructor(): RecyclerView.Adapter<MovieImagesAdapter.Holder>() {

    private val diffUtil = object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recylerListDiffer = AsyncListDiffer(this, diffUtil)

    var images: List<String>
        get() = recylerListDiffer.currentList
        set(value) = recylerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImagesAdapter.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<MovieImagesItemLayoutBinding>(inflater, R.layout.movie_images_item_layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: MovieImagesAdapter.Holder, position: Int) {
        val imageUrl = images[position]
        holder.item.imageUrl = imageUrl
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class Holder(var item: MovieImagesItemLayoutBinding): RecyclerView.ViewHolder(item.root)

}