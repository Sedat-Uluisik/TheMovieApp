package com.sedat.themovieapp.api

import com.sedat.themovieapp.model.Language
import com.sedat.themovieapp.model.Movie
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.model.movieimages.MovieImages
import com.sedat.themovieapp.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    //https://api.themoviedb.org/3/movie/popular?api_key=api_key&language=tr&page=1
    @GET("/3/movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>

    //https://api.themoviedb.org/3/configuration/languages?api_key=api_key
    @GET("/3/configuration/languages")
    suspend fun getLanguages(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Language>

    //https://api.themoviedb.org/3/movie/filmId?api_key={api_key}
    @GET("/3/movie/{movie_id}?")
    suspend fun getMovie(
            @Path("movie_id") movie_id: Int,
            @Query("language") language: String,
            @Query("api_key") apiKey: String = API_KEY
    ): Response<Result>

    //https://api.themoviedb.org/3/movie/550(film id)/images?api_key=api_key
    @GET("/3/movie/{movie_id}/images")
    suspend fun getMovieImages(
            @Path("movie_id") movie_id: Int,
            @Query("api_key") apiKey: String = API_KEY
    ):Response<MovieImages>

    //https://api.themoviedb.org/3/search/movie?api_key=api_key&language=tr&query=Jack+Reacher
    @GET("/3/search/movie")
    suspend fun searchMovie(
            @Query("query") query: String,
            @Query("language") language: String,
            @Query("api_key") apiKey: String = API_KEY
    ):Response<Movie>

    //https://api.themoviedb.org/3/trending/movie/{time_window}?api_key=api_key&language=tr&region=tr&page=3
    @GET("/3/trending/movie/{time_window}")
    suspend fun getTrendMovies(
            @Path("time_window") time: String,
            @Query("language") language: String,
            @Query("region") region: String,
            @Query("page") page: Int,
            @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>
}