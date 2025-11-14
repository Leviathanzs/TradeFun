package kiokyky.app.tradefun.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import kiokyky.app.tradefun.R
import kiokyky.app.tradefun.ui.LiveChartFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class LiveChartFragmentTest {

    @Test
    fun fragment_shouldLaunchSuccessfully() {
        val scenario = launchFragmentInContainer<LiveChartFragment>(
            themeResId = R.style.Theme_TradeFun
        )
        scenario.onFragment { fragment ->
            assertTrue(fragment.isVisible)
        }
    }
}