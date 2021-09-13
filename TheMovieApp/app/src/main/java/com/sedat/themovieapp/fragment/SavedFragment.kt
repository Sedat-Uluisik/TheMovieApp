package com.sedat.themovieapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sedat.themovieapp.R
import com.sedat.themovieapp.adapter.SavedMovieAdapter
import com.sedat.themovieapp.databinding.FragmentSavedBinding
import com.sedat.themovieapp.viewmodel.SavedMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SavedFragment @Inject constructor(
        private val savedMovieAdapter: SavedMovieAdapter
) : Fragment() {

    private var fragmentBinding: FragmentSavedBinding ?= null
    private val binding get() = fragmentBinding!!

    private lateinit var viewModel: SavedMovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentSavedBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(SavedMovieViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerSaved.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSaved.adapter = savedMovieAdapter

        savedMovieAdapter.setOnItemClickListener {
            val action = SavedFragmentDirections.actionSavedFragment2ToDetailsFragment(it, true)
            findNavController().navigate(action)
        }

        getData()
    }

    private fun getData(){
        lifecycleScope.launch {
            viewModel.savedMovies().collectLatest {
                savedMovieAdapter.submitData(it)
            }
        }
    }
}