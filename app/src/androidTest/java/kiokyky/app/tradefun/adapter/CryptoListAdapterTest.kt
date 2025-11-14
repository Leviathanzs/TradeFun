package kiokyky.app.tradefun.adapter

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kiokyky.app.tradefun.model.CoinMarketItem
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CryptoListAdapterTest {

    private lateinit var adapter: CryptoListAdapter
    private lateinit var parent: ViewGroup

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        parent = FrameLayout(context)
        adapter = CryptoListAdapter {}
    }

    @Test
    fun adapter_shouldReflectSubmittedListSize() {
        val dummyList = listOf(
            CoinMarketItem("bitcoin", "btc", "Bitcoin", "https://example.com/btc.png", 65000.0),
            CoinMarketItem("ethereum", "eth", "Ethereum", "https://example.com/eth.png", 3500.0)
        )

        adapter.submitList(dummyList)
        assertEquals(2, adapter.itemCount)
    }
}