package com.sedat.themovieapp.repo

import androidx.lifecycle.MutableLiveData
import com.sedat.themovieapp.model.Language
import com.sedat.themovieapp.model.Movie
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.model.SavedMovie
import com.sedat.themovieapp.model.movieimages.MovieImages
import com.sedat.themovieapp.util.Resource

class FakeMovieRepository: MovieRepositoryInterface {

    private var savedMoveList = mutableListOf<SavedMovie>()

    //retrofit functions

    override suspend fun getMovies(page: Int): Resource<Movie> {
        val myResult = Result(false, "aa", listOf(),
            1, "en", "title", "overview",
            1.0f, "poster/path", "date", "title", false,
        1.0f, 1)

        return Resource.success(Movie(0, listOf(myResult), 0, 0))

    }

    override suspend fun getLanguages(): Resource<Language> {
        return Resource.success(Language())
    }

    override suspend fun getMovie(movie_id: Int): Resource<Result> {
        return Resource.success(
            Result(false, "", listOf(), 0, "",
                "", "", 0.0f, "", "",
                "", false, 0.0f, 0))
    }

    override suspend fun getMovieImages(movie_id: Int): Resource<MovieImages> {
        return Resource.success(MovieImages(listOf(), 0, listOf(), listOf()))
    }

    override suspend fun searchMovie(query: String): Resource<Movie> {
        return Resource.success(Movie(0, listOf(), 0, 0))
    }

    override suspend fun getTrendMovies(time: String, page: Int): Resource<Movie> {
        val myResult1 = Result(false, "aa", listOf(),
            1, "", "", "",
            1.0f, "", "", "", false,
            1.0f, 1)
        val myResult2 = Result(false, "bb", listOf(),
            2, "", "", "",
            1.0f, "", "", "", false,
            1.0f, 1)
        val myResult3 = Result(false, "cc", listOf(),
            3, "", "", "",
            1.0f, "", "", "", false,
            1.0f, 1)
        val myResult4 = Result(false, "cc", listOf(),
            4, "", "", "",
            1.0f, "", "", "", false,
            1.0f, 1)

        return Resource.success(Movie(0, listOf(myResult1, myResult2, myResult3, myResult4), 0, 0))
    }

    //Room functions

    override suspend fun getSavedMovies(size: Int): List<SavedMovie> {
        return savedMoveList
    }

    override suspend fun saveData(savedMovie: SavedMovie) {
        savedMoveList.add(savedMovie)
    }

    override suspend fun deleteData(savedMovie: SavedMovie) {
        savedMoveList.remove(savedMovie)
    }

    override suspend fun deleteDataFromId(id: Int) {
        savedMoveList.removeAt(0)
    }

    override suspend fun getMovieFromId(id: Int): SavedMovie {
        return savedMoveList[0]
    }
}