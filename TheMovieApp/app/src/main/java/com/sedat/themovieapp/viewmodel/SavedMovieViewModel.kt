package com.sedat.themovieapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sedat.themovieapp.model.SavedMovie
import com.sedat.themovieapp.paging.RoomPagingSource
import com.sedat.themovieapp.repo.MovieRepositoryInterface
import com.sedat.themovieapp.room.MovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SavedMovieViewModel @Inject constructor(
        private val repositoryInterface: MovieRepositoryInterface,
        @ApplicationContext private val application: Context
): BaseViewModel(application as Application){

    fun savedMovies(): Flow<PagingData<SavedMovie>>{
        return Pager(
                config = PagingConfig(
                        pageSize = 3,
                        enablePlaceholders = false //Son veriye gelinidiğine tekrar başa dönmeyi engelledik.
                ),
                pagingSourceFactory = {
                    RoomPagingSource(repositoryInterface)
                }
        ).flow.cachedIn(viewModelScope)
    }
}