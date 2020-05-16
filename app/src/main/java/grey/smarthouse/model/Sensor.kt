package grey.smarthouse.model

data class Sensor(
        private val num: Int,
        var temp: Float = 0.toFloat(),
        var location: String = "",
        var description: String = "",
        var type: String = "ds18b20"
)
