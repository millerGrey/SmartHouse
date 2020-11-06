package grey.smarthouse.data

import androidx.room.*


@Entity(tableName = "locationTable")
data class Location(
    @PrimaryKey()
    var name: String=""
)

data class LocationWithLists (
    @Embedded
    var location: Location,
    @Relation(parentColumn = "name", entityColumn = "location")
    var sensors: List<SensorRoom>
)