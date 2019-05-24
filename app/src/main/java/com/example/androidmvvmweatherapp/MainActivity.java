package com.example.androidmvvmweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmvvmweatherapp.model.Weather;
import com.example.androidmvvmweatherapp.services.DummyWeatherService;
import com.example.androidmvvmweatherapp.services.IWeatherService;
import com.example.androidmvvmweatherapp.services.WeatherServiceAsyncTask;

public class MainActivity extends AppCompatActivity {

    private EditText mCityEditText;
    private TextView mTemperatureTextView;
    private TextView mDescriptionTextView;
    private ImageView mImageView;

    private IWeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherService = new DummyWeatherService();

        mCityEditText = findViewById(R.id.cityEditText);
        mTemperatureTextView = findViewById(R.id.temperatureTextView);
        mDescriptionTextView = findViewById(R.id.descriptionTextView);
        mImageView = findViewById(R.id.weatherImageView);
    }


    public void searchClickHandler(View view) {

        /*String city = mCityEditText.getText().toString();
        if (isInputDataValid(city)) {
            WeatherServiceAsyncTask weatherServiceAsyncTask =
                    new WeatherServiceAsyncTask(mTemperatureTextView, mDescriptionTextView, mImageView);
            weatherServiceAsyncTask.execute(city);
        } else {
            Toast.makeText(this, R.string.input_error, Toast.LENGTH_SHORT).show();
        }*/

        Weather weather = weatherService.getCurrentWeather("Test City");
        mTemperatureTextView.setText(weather.getTemperature() + "");
        mDescriptionTextView.setText(weather.getDescription());
        mImageView.setImageBitmap(weather.getBitmapImage());
     }

    private boolean isInputDataValid(String city) {
        return city != null && city.length() > 2;
    }
}
