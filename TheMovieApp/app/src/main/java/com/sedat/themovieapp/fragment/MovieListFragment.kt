package com.sedat.themovieapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedat.themovieapp.MySharedPref
import com.sedat.themovieapp.adapter.MovieListAdapter
import com.sedat.themovieapp.databinding.FragmentMovieListBinding
import com.sedat.themovieapp.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment @Inject constructor(
    val adapter: MovieListAdapter,
    private val mySharedPref: MySharedPref
) : Fragment(), AdapterView.OnItemSelectedListener{

    private var fragmentBinding: FragmentMovieListBinding ?= null
    private val binding get() = fragmentBinding!!

    lateinit var viewModel: MovieListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MovieListViewModel::class.java)
        viewModel.getLanguages()

        binding.recylerMovieList.layoutManager = LinearLayoutManager(requireContext())
        binding.recylerMovieList.adapter = adapter

        binding.spinner.onItemSelectedListener = this

        binding.languageText.text = mySharedPref.getLanguage()

        adapter.setOnItemClickListener {
            val action = MovieListFragmentDirections.actionMovieListFragmentToDetailsFragment(it, false)
            findNavController().navigate(action)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.retry()
            adapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observer()
    }

    private fun observer(){

        lifecycleScope.launch {
            viewModel.data().collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener {
            fragmentBinding?.progressBar?.visibility = if(adapter.itemCount == 0 || it.refresh is LoadState.Loading) View.VISIBLE else View.GONE
            fragmentBinding?.recylerMovieList?.visibility = if(adapter.itemCount == 0 || it.refresh is LoadState.Loading) View.GONE else View.VISIBLE

        }

        viewModel.languages.observe(viewLifecycleOwner, Observer {
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = spinnerAdapter
        })
    }

    //spinner functions
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val language = parent?.getItemAtPosition(position).toString()
        if(language.isNotEmpty()){
            mySharedPref.saveLanguage(language)
            binding.languageText.text = mySharedPref.getLanguage()
            lifecycleScope.launch {
                viewModel.data().collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}