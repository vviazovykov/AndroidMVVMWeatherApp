package com.example.androidmvvmweatherapp.model;

import android.graphics.Bitmap;

public class Weather {

    private int mTemperature;
    private String mDescription;
    private Bitmap mBitmapImage;

    public int getTemperature() {
        return mTemperature;
    }

    public void setTemperature(int mTemperature) {
        this.mTemperature = mTemperature;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Bitmap getBitmapImage() {
        return mBitmapImage;
    }

    public void setBitmapImage(Bitmap mBitmapImage) {
        this.mBitmapImage = mBitmapImage;
    }
}
