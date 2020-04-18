package grey.smarthouse.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import java.util.UUID;

import grey.smarthouse.database.converters.*;

/**
 * Created by GREY on 30.04.2018.
 */
@Entity(tableName = "relayTable")
public class Relay {

    public final static int HAND_MODE = 2;
    public final static int TEMP_MODE = 0;
    public final static int TIME_MODE = 1;

    @PrimaryKey
    @NonNull
    @TypeConverters({IdConverter.class})
    private UUID mId;
    private String mDescription;
    private int mNumber;
    private int mMode;
    private int mTopTemp;
    private int mBotTemp;
    private int mPeriodTime;
    private int mDurationTime;
    private int mSensNum;

    public Relay() {
        this(UUID.randomUUID());
    }

    public Relay(UUID id) {
        mId = id;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public void setDescription(String description) {
        mDescription = description;

    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public void setMode(int mode) {
        mMode = mode;
    }

    public void setTopTemp(int topTemp) {
        mTopTemp = topTemp;
    }

    public void setBotTemp(int botTemp) {
        mBotTemp = botTemp;
    }

    public void setPeriodTime(int periodTime) {
        mPeriodTime = periodTime;
    }

    public void setDurationTime(int durationTime) {
        mDurationTime = durationTime;
    }

    public void setSensNum(int sensNum) {
        mSensNum = sensNum;
    }

    public UUID getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getNumber() {
        return mNumber;
    }

    public int getMode() {
        return mMode;
    }

    public int getTopTemp() {
        return mTopTemp;
    }

    public int getBotTemp() {
        return mBotTemp;
    }

    public int getPeriodTime() {
        return mPeriodTime;
    }

    public int getDurationTime() {
        return mDurationTime;
    }

    public int getSensNum() {
        return mSensNum;
    }
}
