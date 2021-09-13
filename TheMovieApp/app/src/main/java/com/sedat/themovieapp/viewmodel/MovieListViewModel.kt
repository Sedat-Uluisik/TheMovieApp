package com.sedat.themovieapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sedat.themovieapp.model.Language
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.paging.RetrofitPagingSource
import com.sedat.themovieapp.repo.MovieRepositoryInterface
import com.sedat.themovieapp.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepositoryInterface,
    @ApplicationContext private val application: Context
): BaseViewModel(application as Application) {

    private var languageList: ArrayList<String> = ArrayList()
    var languages = MutableLiveData<List<String>>()
    fun getLanguages(){
        launch {
            if(repository.getLanguages().status == Status.SUCCESS){

                languageList.add("")
                languageList.addAll(repository.getLanguages().let {
                    it.data!!.map { languageItem ->
                        languageItem.iso6391
                    }
                }.sorted())

                languages.value = languageList
            }
        }
    }

    fun data(): Flow<PagingData<Result>>{
        return Pager(
            config = PagingConfig(20, 60, enablePlaceholders = false),
            pagingSourceFactory = {
                RetrofitPagingSource(repository)
            }
        ).flow.cachedIn(viewModelScope)
    }
}