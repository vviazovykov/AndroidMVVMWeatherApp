package com.example.androidmvvmweatherapp.services;

import android.os.AsyncTask;
import android.util.Log;

import com.example.androidmvvmweatherapp.converters.WeatherDeserializer;
import com.example.androidmvvmweatherapp.model.Weather;
import com.example.androidmvvmweatherapp.presenters.MainActivityPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherServiceAsyncTask extends AsyncTask<String, Void, Weather> {

    private static final String LOG_TAG = WeatherServiceAsyncTask.class.getSimpleName();

    private static final String OPEN_API_WEATHER_KEY = "60157a0b8341f921509717e43ed101fc";

    private MainActivityPresenter.View mPresenterView;

    public WeatherServiceAsyncTask(MainActivityPresenter.View presenter) {
        this.mPresenterView = presenter;
    }

    @Override
    protected Weather doInBackground(String... strings) {

        if (strings != null && strings.length > 0) {
            String city = strings[0];
            String address = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric&format=json&lang=en", city, OPEN_API_WEATHER_KEY);

            try {
                URL url = new URL(address);
                HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String result = stringBuilder.toString();

                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Weather.class, new WeatherDeserializer());
                Gson gson = builder.create();

                return gson.fromJson(result, Weather.class);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Exception is occurred", e);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);

        if (weather != null) {
            mPresenterView.updateTemperature(weather.getTemperature());
            mPresenterView.updateDescription(weather.getDescription());
            mPresenterView.updateImage(weather.getImageUrl());
        } else {
            mPresenterView.displayServiceErrorMessage();
        }
    }
}
