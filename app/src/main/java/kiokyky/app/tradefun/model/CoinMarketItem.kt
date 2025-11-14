package kiokyky.app.tradefun.model

data class CoinMarketItem(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Double
)