package com.sedat.themovieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    //Bu sınıf, viewModel içerisinde coroutine scope açmak için kullanıldı.

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() =job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}