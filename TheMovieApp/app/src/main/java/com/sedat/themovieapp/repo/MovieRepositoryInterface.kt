package com.sedat.themovieapp.repo

import com.sedat.themovieapp.model.Language
import com.sedat.themovieapp.model.Movie
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.model.SavedMovie
import com.sedat.themovieapp.model.movieimages.MovieImages
import com.sedat.themovieapp.util.Resource

interface MovieRepositoryInterface {

    //Retrofit fonksiyonları
    suspend fun getMovies(page: Int): Resource<Movie>
    suspend fun getLanguages(): Resource<Language>
    suspend fun getMovie(movie_id: Int): Resource<Result>
    suspend fun getMovieImages(movie_id: Int): Resource<MovieImages>
    suspend fun searchMovie(query: String): Resource<Movie>
    suspend fun getTrendMovies(time: String, page: Int): Resource<Movie>


    //Room fonksiyonları
    suspend fun getSavedMovies(size: Int): List<SavedMovie>
    suspend fun saveData(savedMovie: SavedMovie)
    suspend fun deleteData(savedMovie: SavedMovie)
    suspend fun deleteDataFromId(id: Int)
    suspend fun getMovieFromId(id: Int): SavedMovie
}