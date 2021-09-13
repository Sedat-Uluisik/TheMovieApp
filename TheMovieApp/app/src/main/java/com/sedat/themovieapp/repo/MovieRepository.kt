package com.sedat.themovieapp.repo

import com.sedat.themovieapp.MySharedPref
import com.sedat.themovieapp.api.MovieApi
import com.sedat.themovieapp.model.movieimages.Backdrop
import com.sedat.themovieapp.model.Language
import com.sedat.themovieapp.model.Movie
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.model.SavedMovie
import com.sedat.themovieapp.model.movieimages.MovieImages
import com.sedat.themovieapp.room.MovieDao
import com.sedat.themovieapp.util.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val mySharedPref: MySharedPref
): MovieRepositoryInterface {

    override suspend fun getMovies(page: Int): Resource<Movie> {
        return try {
            val language = mySharedPref.getLanguage()
            val response = movieApi.getMovies(page, language)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }else
                Resource.error("Error", null)
        }catch (e: Exception){
            Resource.error("No data!", null)
        }
    }

    override suspend fun getLanguages(): Resource<Language> {
        return try {
            val response = movieApi.getLanguages()
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }else
                Resource.error("Error", null)
        }catch (e: Exception){
            Resource.error("No data!", null)
        }
    }

    override suspend fun getMovie(movie_id: Int): Resource<Result> {
        return try {
            val response = movieApi.getMovie(movie_id, mySharedPref.getLanguage())
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            }
            else
                Resource.error("Error!", null)
        }catch (e: Exception){
            Resource.error("No data!", null)
        }
    }

    override suspend fun getMovieImages(movie_id: Int): Resource<MovieImages> {
        return try {
            val response = movieApi.getMovieImages(movie_id)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            }
            else
                Resource.error("Error!", null)
        }catch (e: Exception){
            Resource.error("No data!", null)
        }
    }

    override suspend fun searchMovie(query: String): Resource<Movie> {
        return try {
            val response = movieApi.searchMovie(query, mySharedPref.getLanguage())
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            }else
                Resource.error("Error!", null)
        }catch (e:Exception){
            Resource.error("No data!", null)
        }
    }

    override suspend fun getTrendMovies(time: String, page: Int): Resource<Movie> {
        return try {
            val response = movieApi.getTrendMovies(time, mySharedPref.getLanguage(), mySharedPref.getLanguage(), page)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            }else
                Resource.error("Error!", null)
        }catch (e:Exception){
            Resource.error("No data!", null)
        }
    }

    override suspend fun getSavedMovies(size: Int): List<SavedMovie> {
        return movieDao.getSavedMovies(size)
    }

    override suspend fun saveData(savedMovie: SavedMovie) {
        movieDao.saveData(savedMovie)
    }

    override suspend fun deleteData(savedMovie: SavedMovie) {
        movieDao.deleteData(savedMovie)
    }

    override suspend fun deleteDataFromId(id: Int) {
        movieDao.deleteDataFromId(id)
    }

    override suspend fun getMovieFromId(id: Int): SavedMovie {
        return movieDao.getMovieFromId(id)
    }


}