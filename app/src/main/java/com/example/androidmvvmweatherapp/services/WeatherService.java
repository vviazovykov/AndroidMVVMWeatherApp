package com.example.androidmvvmweatherapp.services;

import com.example.androidmvvmweatherapp.converters.WeatherDeserializer;
import com.example.androidmvvmweatherapp.model.Weather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherService {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build();

    private OpenWeatherMapService mService = mRetrofit.create(OpenWeatherMapService.class);

    private interface OpenWeatherMapService {
        @GET("weather?units=metric&format=json&lang=en")
        Call<Weather> getCurrentWeather(@Query("q") String city, @Query("appid") String apiKey);
    }

    private Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Weather.class, new WeatherDeserializer());
        return builder.create();
    }

    public Call<Weather> getWeatherData(String city, String apiKey) {
        return mService.getCurrentWeather(city, apiKey);
    }
}
