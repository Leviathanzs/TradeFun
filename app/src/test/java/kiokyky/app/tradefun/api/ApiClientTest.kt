package kiokyky.app.tradefun.api

import retrofit2.HttpException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class ApiClientTest {

    @Test
    fun getTopCoins_shouldReturnValidData() = runTest {
        val coins = ApiClient.service.getTopCoins()
        assertTrue(coins.isNotEmpty())
        val first = coins.first()
        assertNotNull(first.id)
        assertTrue(first.current_price > 0)
    }

    @Test
    fun getTopCoins_shouldHandleHttpError() = runBlocking {
        val server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(404))
        server.start()

        val mockUrl = server.url("/").toString()
        val fakeService = ApiClient.create(mockUrl)

        try {
            fakeService.getTopCoins()
            fail("Expected HttpException")
        } catch (e: HttpException) {
            assertEquals(404, e.code())
        } finally {
            server.shutdown()
        }
    }

}