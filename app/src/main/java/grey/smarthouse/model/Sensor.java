package grey.smarthouse.model;

public class Sensor {
    private int num;
    private float mTemp;
    private String mLocation;
    private String mDescription;
    private String mType;

    public Sensor(int num) {
        this.num = num;
        mLocation = "";
        mDescription = "";
        mType = "ds18b20";
    }

    public void setTemp(float temp) {
        mTemp = temp;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setType(String type) {
        mType = type;
    }

    public float getTemp() {
        return mTemp;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getType() {
        return mType;
    }
}
