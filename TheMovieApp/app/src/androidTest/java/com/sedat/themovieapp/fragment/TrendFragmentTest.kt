package com.sedat.themovieapp.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagingSource
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import com.sedat.themovieapp.MovieFragmentFactory
import com.sedat.themovieapp.R
import com.sedat.themovieapp.launchFragmentInHiltContainer
import com.sedat.themovieapp.model.Result
import com.sedat.themovieapp.paging.TrendingFragmentPagingSource
import com.sedat.themovieapp.repo.FakeMovieRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class TrendFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var factory: MovieFragmentFactory

    lateinit var pagingSource: TrendingFragmentPagingSource

    @Before
    fun setup(){
        hiltRule.inject()
        pagingSource = TrendingFragmentPagingSource(FakeMovieRepository(), "day")
    }

    @Test
    fun doesTextViewAndOtherFabBtnAppearWhenMainFabBtnIsClicked(){
        /*
        Fab butonuna tıklanınca diğer fab butonlar ve textler görünüyor mu? testi
         */
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<TrendFragment>(
            fragmentFactory = factory
        ){
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.main_fab)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.textViewDay)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textViewWeek)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.fabBtnDay)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.fabBtnWeek)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun doesTextViewAndOtherFabBtnHideWhenMainFabBtnIsClicked(){
        /*
        Fab butonuna tıklanınca diğer fab butonlar ve textler gizleniyor mu? testi
         */
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<TrendFragment>(
            fragmentFactory = factory
        ){
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.main_fab)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.textViewDay)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textViewWeek)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.fabBtnDay)).check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.fabBtnWeek)).check(matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.main_fab)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.textViewDay)).check(matches(withEffectiveVisibility(
            ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withId(R.id.textViewWeek)).check(matches(withEffectiveVisibility(
            ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withId(R.id.fabBtnDay)).check(matches(withEffectiveVisibility(
            ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withId(R.id.fabBtnWeek)).check(matches(withEffectiveVisibility(
            ViewMatchers.Visibility.GONE)))
    }

    val myResult1 = Result(false, "aa", listOf(),
        1, "", "", "",
        1.0f, "", "", "", false,
        1.0f, 1)
    val myResult2 = Result(false, "bb", listOf(),
        2, "", "", "",
        1.0f, "", "", "", false,
        1.0f, 1)
    val myResult3 = Result(false, "cc", listOf(),
        3, "", "", "",
        1.0f, "", "", "", false,
        1.0f, 1)
    val myResult4 = Result(false, "cc", listOf(),
        4, "", "", "",
        1.0f, "", "", "", false,
        1.0f, 1)

    private val fakeMovies = listOf(myResult1, myResult2, myResult3, myResult4)

    @Test
    fun testRefreshAppendAndPrependForPaging() = runBlockingTest{
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<TrendFragment>(
            fragmentFactory = factory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        refreshPageTest()
        appendPageTest()
        prependPageTest()
    }

    private suspend fun refreshPageTest(){
        val expectedResult = PagingSource.LoadResult.Page(
            data = fakeMovies,
            prevKey = null,
            nextKey = 2
        )

        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )

    }

    private suspend fun appendPageTest(){
        val expectedResult = PagingSource.LoadResult.Page(
            data = fakeMovies,
            prevKey = 1,
            nextKey = 3
        )

        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 2,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )

    }

    private suspend fun prependPageTest(){
        val expectedResult = PagingSource.LoadResult.Page(
            data = fakeMovies,
            prevKey = null,
            nextKey = 2
        )

        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }


}