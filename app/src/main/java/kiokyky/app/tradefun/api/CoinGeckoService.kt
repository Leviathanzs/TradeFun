package kiokyky.app.tradefun.api


import kiokyky.app.tradefun.model.ChartResponse
import kiokyky.app.tradefun.model.CoinMarketItem
import kiokyky.app.tradefun.model.CryptoItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoService {

    @GET("coins/list")
    suspend fun getCryptoList(): List<CryptoItem>

    @GET("coins/markets")
    suspend fun getTopCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1
    ): List<CoinMarketItem>

    @GET("coins/{id}/market_chart")
    suspend fun getMarketChart(
        @Path("id") coinId: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("days") days: String = "1"
    ): ChartResponse

    @GET("coins/{id}/ohlc")
    suspend fun getOhlcData(
        @Path("id") id: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("days") days: String = "1"
    ): List<List<Double>>
}