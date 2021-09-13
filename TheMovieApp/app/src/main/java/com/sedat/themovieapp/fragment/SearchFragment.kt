package com.sedat.themovieapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedat.themovieapp.R
import com.sedat.themovieapp.adapter.MovieListAdapter
import com.sedat.themovieapp.adapter.SearchAdapter
import com.sedat.themovieapp.databinding.FragmentSearchBinding
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment @Inject constructor(
    val searchAdapter: SearchAdapter
) : Fragment() {

    private lateinit var dataBinding: FragmentSearchBinding
    lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearData()

        var job: Job ?= null
        dataBinding.searchEditText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(800)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchMovie(it.toString())
                    }else
                        clearData()
                } ?: clearData()
            }
        }

        dataBinding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        dataBinding.recyclerSearch.adapter = searchAdapter

        observe()
    }

    private fun observe(){
        viewModel.searchMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                searchAdapter.searchMovies = it.results
            }
        })

        viewModel.loadingAndErrorData.observe(viewLifecycleOwner, Observer {
            if(it){
                dataBinding.progressBar.visibility = View.VISIBLE
                dataBinding.recyclerSearch.visibility = View.GONE
            }else{
                dataBinding.progressBar.visibility = View.GONE
                dataBinding.recyclerSearch.visibility = View.VISIBLE
            }
        })
    }

    private fun clearData(){
        viewModel.clearData()
        searchAdapter.searchMovies = listOf()
    }
}