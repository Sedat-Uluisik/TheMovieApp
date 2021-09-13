package com.sedat.themovieapp.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sedat.themovieapp.model.movieimages.Backdrop
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.model.SavedMovie
import com.sedat.themovieapp.model.movieimages.MovieImages
import com.sedat.themovieapp.repo.MovieRepositoryInterface
import com.sedat.themovieapp.room.MovieDao
import com.sedat.themovieapp.util.Resource
import com.sedat.themovieapp.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsFragmentViewModel @Inject constructor(
        private val repository: MovieRepositoryInterface,
        @ApplicationContext private val application: Context
): BaseViewModel(application as Application) {

    var movie = MutableLiveData<Result?>()
    fun getMovie(movie_id: Int){
        launch {
            val response = repository.getMovie(movie_id).data

            if(response != null)
                movie.value = response
        }
    }

    var movieImages = MutableLiveData<MovieImages?>()
    fun getMovieImages(movie_id: Int){
        launch {
            val response = repository.getMovieImages(movie_id)
            movieImages.value = response.data
        }
    }

    fun clearData(){
        movieImages.value = null
        movie.value = null
    }

    fun saveMovieForRoomDb(movie_id: Int, url: String, title: String, date: String, imdb: Float, overview: String){

        launch {
            val movie = SavedMovie(movie_id, url, title, date, imdb, overview)
            repository.saveData(movie)

            Toast.makeText(application, "Movie Saved", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun isFavouriteMovie(movie_id: Int): Boolean{
        val movie = repository.getMovieFromId(movie_id)

        return movie != null
    }

    fun deleteMovieFromRoom(movie_id: Int){
        launch {
            repository.deleteDataFromId(movie_id)
        }
    }

}