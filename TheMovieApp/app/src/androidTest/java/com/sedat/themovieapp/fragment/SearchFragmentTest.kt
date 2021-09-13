package com.sedat.themovieapp.fragment

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.sedat.themovieapp.MovieFragmentFactory
import com.sedat.themovieapp.R
import com.sedat.themovieapp.adapter.SearchAdapter
import com.sedat.themovieapp.launchFragmentInHiltContainer
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.repo.FakeMovieRepository
import com.sedat.themovieapp.viewmodel.SearchViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SearchFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: MovieFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationSearchFragmentToDetailsFragment(){
        /*
        Recylerview da item e tıklanınca detaylar fragment e gidiyor mu? testi.
         */

        val navController = Mockito.mock(NavController::class.java)

        val fakeResult = Result(false, "aa", listOf(),
            1, "en", "title", "overview",
            1.0f, "poster/path", "date", "title", false,
            1.0f, 1)

        val testViewModel = SearchViewModel(FakeMovieRepository())

        launchFragmentInHiltContainer<SearchFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            searchAdapter.searchMovies = listOf(fakeResult)
            viewModel = testViewModel
        }

        //Recyclerview de 0.item e tıkla.
        Espresso.onView(ViewMatchers.withId(R.id.recycler_search)).perform(
            RecyclerViewActions.actionOnItemAtPosition<SearchAdapter.Holder>(
                0, ViewActions.click()
            )
        )

        //Recylerview de 0.item e tıklanınca DetailsFragment() e gidiyor mu? doğrula.
        Mockito.verify(navController).navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(1)
        )
    }

    @Test
    fun checkSearchEdittextIsDisplayed(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<SearchFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.search_edit_text)).check(matches(ViewMatchers.isDisplayed()))
    }
}