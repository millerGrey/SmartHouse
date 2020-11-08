package grey.smarthouse.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "locationTable")
data class Location(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var name: String = ""
)

data class LocationWithLists (
    @Embedded
    var location: Location,
    @Relation(parentColumn = "name", entityColumn = "location")
    var sensors: List<SensorRoom>
)