package com.example.androidmvvmweatherapp.services;

import com.example.androidmvvmweatherapp.model.Weather;

public interface IWeatherService {

    Weather getCurrentWeather(String cityName);
}
