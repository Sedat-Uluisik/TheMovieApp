package com.sedat.themovieapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.sedat.themovieapp.adapter.MovieImagesAdapter
import com.sedat.themovieapp.adapter.MovieListAdapter
import com.sedat.themovieapp.adapter.SavedMovieAdapter
import com.sedat.themovieapp.adapter.SearchAdapter
import com.sedat.themovieapp.fragment.*
import javax.inject.Inject

class MovieFragmentFactory @Inject constructor(
    private val movieListAdapter: MovieListAdapter,
    private val imagesAdapter: MovieImagesAdapter,
    private val searchAdapter: SearchAdapter,
    private val savedMovieAdapter: SavedMovieAdapter,
    private val mySharedPref: MySharedPref
): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            DetailsFragment::class.java.name -> DetailsFragment(imagesAdapter)
            MovieListFragment::class.java.name -> MovieListFragment(movieListAdapter, mySharedPref)
            SearchFragment::class.java.name -> SearchFragment(searchAdapter)
            TrendFragment::class.java.name -> TrendFragment(movieListAdapter)
            SavedFragment::class.java.name -> SavedFragment(savedMovieAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}