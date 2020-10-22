package grey.smarthouse.data

interface LocalDataSource: DataSource {

    fun insert(relay: Relay)
    fun delete(relay: Relay)

}