package com.sedat.themovieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.themovieapp.R
import com.sedat.themovieapp.clicklistener.MyClickListener
import com.sedat.themovieapp.databinding.SearchItemLayoutBinding
import com.sedat.themovieapp.fragment.SearchFragmentDirections
import com.sedat.themovieapp.model.Result
import javax.inject.Inject

class SearchAdapter @Inject constructor(): RecyclerView.Adapter<SearchAdapter.Holder>(), MyClickListener {

    private val diffUtil = object :DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var searchMovies: List<Result>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<SearchItemLayoutBinding>(inflater, R.layout.search_item_layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.Holder, position: Int) {
        val movies = searchMovies[position]

        holder.item.movie = movies
        holder.item.clickListener = this
    }

    override fun getItemCount(): Int {
        return searchMovies.size
    }

    class Holder(var item: SearchItemLayoutBinding): RecyclerView.ViewHolder(item.root)

    override fun onSearchItemClick(view: View, movie_id: Int) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movie_id)
        view.findNavController().navigate(action)
    }

}