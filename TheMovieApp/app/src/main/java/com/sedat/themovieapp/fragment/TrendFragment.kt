package com.sedat.themovieapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sedat.themovieapp.R
import com.sedat.themovieapp.adapter.MovieListAdapter
import com.sedat.themovieapp.databinding.FragmentTrendBinding
import com.sedat.themovieapp.viewmodel.TrendingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TrendFragment @Inject constructor(
    val movieListAdapter: MovieListAdapter
) : Fragment() {

    private var fragmentBinding: FragmentTrendBinding ?= null
    private val binding get() = fragmentBinding!!

    lateinit var viewModel: TrendingViewModel

    private var isFabBtnOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentTrendBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(TrendingViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerTrending.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTrending.adapter = movieListAdapter

        getTrendMovies("day", "Daily")

        binding.fabBtnDay.setOnClickListener {
            getTrendMovies("day", "Daily")
            fabBtnAnim()
        }
        binding.fabBtnWeek.setOnClickListener {
            getTrendMovies("week", "Weekly")
            fabBtnAnim()
        }
        binding.mainFab.setOnClickListener {
            fabBtnAnim()
        }

        movieListAdapter.setOnItemClickListener {
            val action = TrendFragmentDirections.actionTrendFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }

        movieListAdapter.addLoadStateListener {
            fragmentBinding?.progressBarTrend?.visibility = if(movieListAdapter.itemCount == 0 || it.refresh is LoadState.Loading) View.VISIBLE else View.GONE
            fragmentBinding?.recyclerTrending?.visibility = if(movieListAdapter.itemCount == 0 || it.refresh is LoadState.Loading) View.GONE else View.VISIBLE
        }
    }

    private fun getTrendMovies(time: String, title: String){
        binding.trendingText.text = "Trending $title"
        lifecycleScope.launch {
            viewModel.data(time).collectLatest {
                movieListAdapter.submitData(it)
            }
        }
    }

    private fun fabBtnAnim(){
        val fabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open_anim)
        val fabClose = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close_anim)
        val fabRotateClockWis = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clock_wise)
        val fabRotateAntiClockWise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anti_clock_wise)

        if(isFabBtnOpen){
            binding.fabBtnWeek.startAnimation(fabClose)
            binding.fabBtnDay.startAnimation(fabClose)
            binding.textViewWeek.startAnimation(fabClose)
            binding.textViewDay.startAnimation(fabClose)
            binding.mainFab.startAnimation(fabRotateClockWis)
            binding.fabBtnWeek.visibility = View.GONE
            binding.fabBtnDay.visibility = View.GONE
            binding.textViewDay.visibility = View.GONE
            binding.textViewWeek.visibility = View.GONE
            isFabBtnOpen = false
        }else{
            binding.fabBtnWeek.startAnimation(fabOpen)
            binding.fabBtnDay.startAnimation(fabOpen)
            binding.textViewWeek.startAnimation(fabOpen)
            binding.textViewDay.startAnimation(fabOpen)
            binding.mainFab.startAnimation(fabRotateAntiClockWise)
            binding.fabBtnWeek.visibility = View.VISIBLE
            binding.fabBtnDay.visibility = View.VISIBLE
            binding.textViewDay.visibility = View.VISIBLE
            binding.textViewWeek.visibility = View.VISIBLE
            isFabBtnOpen = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }
}