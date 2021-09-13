package com.sedat.themovieapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedat.themovieapp.R
import com.sedat.themovieapp.adapter.MovieImagesAdapter
import com.sedat.themovieapp.clicklistener.FavouriteBtnClickListener
import com.sedat.themovieapp.databinding.FragmentDetailsBinding
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.viewmodel.DetailsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment @Inject constructor(
        private val imagesAdapter: MovieImagesAdapter
) : Fragment(), FavouriteBtnClickListener {

    private lateinit var databinding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(DetailsFragmentViewModel::class.java)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var movieId: Int = -1
        var isSavedMovie = false
        arguments?.let {
            movieId = DetailsFragmentArgs.fromBundle(it).movieId
            isSavedMovie = DetailsFragmentArgs.fromBundle(it).isSaved
        }

        viewModel.clearData()

        if(movieId != -1){

            lifecycleScope.launch {
                viewModel.getMovie(movieId)
                viewModel.getMovieImages(movieId)
                if(isSavedMovie){
                    databinding.favourite.setImageResource(R.drawable.is_favorite_32)
                }else{
                    if(viewModel.isFavouriteMovie(movieId)){
                        databinding.favourite.setImageResource(R.drawable.is_favorite_32)
                    }
                    else{
                        databinding.favourite.setImageResource(R.drawable.ic_baseline_favorite_border_32)
                    }
                }
            }
        }

        databinding.recyclerImages.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        databinding.recyclerImages.adapter = imagesAdapter

        databinding.clickListener = this

        observe()
    }

    private fun observe(){
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            databinding.movie = it
        })

        viewModel.movieImages.observe(viewLifecycleOwner, Observer {
            it?.let { movieImages ->
                imagesAdapter.images = movieImages.backdrops.map { backdrop ->
                    backdrop.filePath
                }
            }
        })
    }

    override fun onFavouriteBtnClick(view: View, movie: Result) {
        lifecycleScope.launch {
            val isFavourite = viewModel.isFavouriteMovie(movie.id)
            if(isFavourite) {
                viewModel.deleteMovieFromRoom(movie.id)
                databinding.favourite.setImageResource(R.drawable.ic_baseline_favorite_border_32)
            }else{
                viewModel.saveMovieForRoomDb(movie.id, movie.posterPath, movie.title, movie.releaseDate, movie.voteAverage, movie.overview)
                databinding.favourite.setImageResource(R.drawable.is_favorite_32)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }
}