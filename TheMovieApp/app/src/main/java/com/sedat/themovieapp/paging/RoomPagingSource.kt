package com.sedat.themovieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sedat.themovieapp.model.SavedMovie
import com.sedat.themovieapp.repo.MovieRepositoryInterface
import com.sedat.themovieapp.room.MovieDao
import javax.inject.Inject

class RoomPagingSource @Inject constructor(
        private val repository: MovieRepositoryInterface
): PagingSource<Int, SavedMovie>() {
    override fun getRefreshKey(state: PagingState<Int, SavedMovie>): Int? {
        return state.anchorPosition?.let{
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SavedMovie> {
        return try {

            val nextPage = params.key ?: 1
            val data = repository.getSavedMovies(params.loadSize)

            LoadResult.Page(
                    data = data,
                    prevKey = if(nextPage == 1) null else nextPage - 1,
                    nextKey = if(data.isNullOrEmpty() || nextPage <= data.size) null else nextPage + 1
            )

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}