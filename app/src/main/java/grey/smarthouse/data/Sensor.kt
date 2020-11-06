package grey.smarthouse.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "sensorTable")
data class SensorRoom(
    @PrimaryKey
    var number: Int = 0,
    var id: String = "",
    var location: String = "",
    var value: Float = 0F,
    var model: String = "ds18b20",
    var type: String = "temperature",
    var description: String = ""
)