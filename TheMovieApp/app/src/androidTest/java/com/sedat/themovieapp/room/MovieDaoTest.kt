package com.sedat.themovieapp.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.sedat.themovieapp.model.SavedMovie
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testRoomDb")
    lateinit var movieDatabase: MovieDatabase

    private lateinit var movieDao: MovieDao

    @Before
    fun setup(){
        hiltRule.inject()
        movieDao = movieDatabase.movieDao()
    }

    @After
    fun teardown(){
        movieDatabase.close()
    }

    @Test
    fun saveDataTest() = runBlockingTest{
        val myMovieTest = SavedMovie(123, "theMovieApp.jpg", "movie", "06-09-2021", 9.5F, "movie description")
        movieDao.saveData(myMovieTest)

        val movieList = movieDao.getSavedMovies(1)
        assertThat(movieList).contains(myMovieTest)
    }

    @Test
    fun getMovieFromIdTest() = runBlockingTest {
        val myMovieTest = SavedMovie(12, "theMovieApp.jpg", "movie", "06-09-2021", 9.5F, "movie description")
        movieDao.saveData(myMovieTest)

        val selectMovie = movieDao.getMovieFromId(12)
        assertThat(selectMovie).isEqualTo(myMovieTest)
    }

    @Test
    fun deleteDataTest() = runBlockingTest {
        val myMovieTest = SavedMovie(12, "theMovieApp.jpg", "movie", "06-09-2021", 9.5F, "movie description")
        val myMovieTest2 = SavedMovie(13, "theMovieApp2.jpg", "movie2", "06-09-2021", 9.5F, "movie description2")
        movieDao.saveData(myMovieTest)
        movieDao.saveData(myMovieTest2)
        movieDao.deleteData(myMovieTest)

        val movieList = movieDao.getSavedMovies(2)
        assertThat(movieList).doesNotContain(myMovieTest)
    }

    @Test
    fun deleteDataFromIdTest() = runBlockingTest {
        val myMovieTest = SavedMovie(14, "theMovieApp.jpg", "movie", "06-09-2021", 9.5F, "movie description")
        val myMovieTest2 = SavedMovie(15, "theMovieApp2.jpg", "movie2", "06-09-2021", 9.5F, "movie description2")
        val myMovieTest3 = SavedMovie(16, "theMovieApp3.jpg", "movie3", "06-09-2021", 9.5F, "movie description3")
        movieDao.saveData(myMovieTest)
        movieDao.saveData(myMovieTest2)
        movieDao.saveData(myMovieTest3)
        movieDao.deleteDataFromId(14)

        val movieList = movieDao.getSavedMovies(3)
        assertThat(movieList).doesNotContain(myMovieTest)
    }
}