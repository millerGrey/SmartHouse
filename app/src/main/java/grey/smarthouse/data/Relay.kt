package grey.smarthouse.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by GREY on 30.04.2018.
 */


enum class RelayMode(val value: Int){
    TEMP(0),
    TIME(1),
    HAND(2),
}
@Entity(tableName = "relayTable")

data class Relay(
        @PrimaryKey
        var id: UUID = UUID.randomUUID(),
        var description: String = "",
        var number: Int = 0,
        var mode: Int = 0,
        var topTemp: Int = 0,
        var botTemp: Int = 0,
        var periodTime: Int = 0,
        var durationTime: Int = 0,
        var sensNum: Int = 0
) {


    companion object {

        val HAND_MODE = 2
        val TEMP_MODE = 0
        val TIME_MODE = 1
    }
}
