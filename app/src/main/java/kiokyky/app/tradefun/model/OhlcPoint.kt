package kiokyky.app.tradefun.model

data class OhlcPoint(
    val time: Long,
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float
)