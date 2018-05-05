package com.example.grey.smarthouse;

import java.util.UUID;

/**
 * Created by GREY on 30.04.2018.
 */

public class Relay {

    public final static int HAND_MODE = 2;
    public final static int TEMP_MODE = 0;
    public final static int TIME_MODE = 1;

    private UUID mId;
    private int mNumber;
    private String mDescription;
    private int mMode;
    private boolean mHot;
    private int mTopTemp;
    private int mBotTemp;
    private int mPeriodTime;
    private int mDurationTime;

    public Relay() {
        this(UUID.randomUUID());
    }

    public Relay(UUID id) {
        mId = UUID.randomUUID();
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

    public void setHot(boolean hot) {
        mHot = hot;
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

    public boolean isHot() {
        return mHot;
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
}
