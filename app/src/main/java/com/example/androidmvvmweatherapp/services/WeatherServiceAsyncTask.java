package com.example.androidmvvmweatherapp.services;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmvvmweatherapp.R;
import com.example.androidmvvmweatherapp.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherServiceAsyncTask extends AsyncTask<String, Void, Weather> {

    private static final String OPEN_API_WEATHER_KEY = "60157a0b8341f921509717e43ed101fc";

    private TextView mTemperatureTextView;
    private TextView mDescriptionTextView;
    private ImageView mImageView;

    public WeatherServiceAsyncTask(TextView mTemperatureTextView, TextView mDescriptionTextView, ImageView mImageView) {
        this.mTemperatureTextView = mTemperatureTextView;
        this.mDescriptionTextView = mDescriptionTextView;
        this.mImageView = mImageView;
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

                Weather weather = new Weather();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray weatherJsonArray = jsonObject.getJSONArray("weather");
                JSONObject weatherElementObject = weatherJsonArray.getJSONObject(0);

                weather.setDescription(weatherElementObject.getString("description"));
                //String icon = weatherElementObject.getString("icon");

                JSONObject mainJsonObject = jsonObject.getJSONObject("main");

                weather.setTemperature((int)mainJsonObject.getDouble("temp"));

                return weather;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);

        if (weather != null) {
            mTemperatureTextView.setText(weather.getTemperature() + " °C");
            mDescriptionTextView.setText(weather.getDescription());
            //mImageView.setImageBitmap(weather.getBitmapImage());
        }
    }
}