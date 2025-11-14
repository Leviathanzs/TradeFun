package kiokyky.app.tradefun.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.filters.MediumTest
import kiokyky.app.tradefun.R
import kiokyky.app.tradefun.adapter.CryptoListAdapter
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class CryptoListFragmentTest {

    @Test
    fun fragment_shouldLaunchSuccessfully() {
        launchFragmentInContainer<CryptoListFragment>(
            themeResId = R.style.Theme_TradeFun
        )

        // Tunggu agar RecyclerView sempat terisi
        Thread.sleep(1500)

        onView(withId(R.id.cryptoRecycler)).check(matches(isDisplayed()))
    }

    @Test
    fun filter_shouldShowMatchingItems() {
        launchFragmentInContainer<CryptoListFragment>(
            themeResId = R.style.Theme_TradeFun
        )

        // Tunggu agar data muncul
        Thread.sleep(1500)

        onView(withId(R.id.searchInput)).perform(typeText("btc"), pressImeActionButton())

        // Tunggu agar filtering sempat terjadi
        Thread.sleep(500)

        onView(withId(R.id.cryptoRecycler)).check(matches(hasMinimumChildCount(1)))
    }

}