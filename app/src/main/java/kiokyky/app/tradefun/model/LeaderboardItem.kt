package kiokyky.app.tradefun.model

data class LeaderboardItem(
    val rank: Int,
    val username: String,
    val asset: String,
    val profitPercent: Double
)