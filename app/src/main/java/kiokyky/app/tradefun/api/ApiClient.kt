package kiokyky.app.tradefun.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun create(baseUrl: String = "https://api.coingecko.com/api/v3/"): CoinGeckoService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CoinGeckoService::class.java)
    }

    val service: CoinGeckoService = create()
}