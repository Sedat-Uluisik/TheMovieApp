package com.sedat.themovieapp.room

import androidx.room.*
import com.sedat.themovieapp.model.SavedMovie

@Dao
interface MovieDao {

    @Query("SELECT * FROM MY_MOVIE WHERE movie_id IN (SELECT movie_id FROM MY_MOVIE ORDER BY RANDOM() LIMIT :size)")
    suspend fun getSavedMovies(size: Int): List<SavedMovie>

    @Query("SELECT * FROM MY_MOVIE WHERE movie_id = :id")
    suspend fun getMovieFromId(id: Int): SavedMovie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveData(savedMovie: SavedMovie)

    @Delete
    suspend fun deleteData(savedMovie: SavedMovie)

    @Query("DELETE FROM MY_MOVIE WHERE movie_id =:id")
    suspend fun deleteDataFromId(id: Int)
}