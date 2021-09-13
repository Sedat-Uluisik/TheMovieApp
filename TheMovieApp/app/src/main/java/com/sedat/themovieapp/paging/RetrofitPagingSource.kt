package com.sedat.themovieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.repo.MovieRepositoryInterface
import javax.inject.Inject

class RetrofitPagingSource @Inject constructor(
    private val movieRepositoryInterface: MovieRepositoryInterface
): PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPage = params.key ?: 1
            val data = movieRepositoryInterface.getMovies(nextPage)
            val response = if(data.data != null && data.data.results.isNotEmpty())
                data.data.results
            else
                listOf()

            LoadResult.Page(
                    data = response,
                    prevKey = if(nextPage == 1) null else nextPage - 1,
                    nextKey = if(response.isEmpty()) null else nextPage + 1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}