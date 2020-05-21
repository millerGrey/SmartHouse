package grey.smarthouse.data

data class Sensor(
    val value: Float = 0F,
    val config:SensorConfig
)
data class SensorConfig(
    private val num: Int,
    val id: String,
    var location: String = "",
    var description: String = "",
    var type: String = "ds18b20"
)
