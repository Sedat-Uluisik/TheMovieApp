package com.sedat.themovieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.paging.TrendingFragmentPagingSource
import com.sedat.themovieapp.repo.MovieRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel@Inject constructor(
        private val repositoryInterface: MovieRepositoryInterface,
): ViewModel() {

    fun data(time: String): Flow<PagingData<Result>>{
        return Pager(
                config = PagingConfig(20, 60, enablePlaceholders = false),
                pagingSourceFactory = {
                    TrendingFragmentPagingSource(repositoryInterface, time)
                }
        ).flow.cachedIn(viewModelScope)
    }

}