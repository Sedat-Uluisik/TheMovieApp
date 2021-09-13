package com.sedat.themovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.themovieapp.model.Movie
import com.sedat.themovieapp.repo.MovieRepositoryInterface
import com.sedat.themovieapp.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
        private val repositoryInterface: MovieRepositoryInterface
): ViewModel()  {

    var loadingAndErrorData = MutableLiveData<Boolean>(false)

    private var searchMovies = MutableLiveData<Movie?>()
    val searchMovie: LiveData<Movie?>
        get() = searchMovies
    fun searchMovie(query: String){
        viewModelScope.launch {
            val response = repositoryInterface.searchMovie(query)
            if(response.status == Status.SUCCESS && response.data != null && response.data.results.isNotEmpty()){
                searchMovies.value = response.data
                loadingAndErrorData.value = false
            }else
                loadingAndErrorData.value = true
        }
    }

    fun clearData(){
        searchMovies.value = null
    }

    override fun onCleared() {
        super.onCleared()
        loadingAndErrorData.value = false
    }
}