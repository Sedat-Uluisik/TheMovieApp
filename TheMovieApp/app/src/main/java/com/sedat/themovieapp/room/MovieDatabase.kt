package com.sedat.themovieapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sedat.themovieapp.model.SavedMovie

@Database(entities = [SavedMovie::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}