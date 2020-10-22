package grey.smarthouse.data

interface RemoteDataSource: DataSource {

    fun getSensorList(): List<SensorConfig>

    fun getRelayStates(): List<String>

    fun setRelayState(num: Int, enable: Boolean)
}