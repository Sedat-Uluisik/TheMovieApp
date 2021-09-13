package com.sedat.themovieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MY_MOVIE")
data class SavedMovie(
        @PrimaryKey(autoGenerate = false)
        val movie_id: Int,
        val movie_url: String,
        val movie_title: String,
        val movie_release_date: String,
        val movie_imdb: Float,
        val movie_overview: String

)