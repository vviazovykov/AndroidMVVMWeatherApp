package com.example.androidmvvmweatherapp.services;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.androidmvvmweatherapp.model.Weather;

public class DummyWeatherService implements IWeatherService {

    @Override
    public Weather getCurrentWeather(String cityName) {

        Weather weather = new Weather();

        weather.setTemperature(26);
        weather.setDescription("sunny");

        Bitmap image = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.GREEN);
        weather.setBitmapImage(image);
        return weather;
    }
}
