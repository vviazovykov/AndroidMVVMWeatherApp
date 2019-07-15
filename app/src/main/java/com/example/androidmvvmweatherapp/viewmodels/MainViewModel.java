package com.example.androidmvvmweatherapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.example.androidmvvmweatherapp.R;
import com.example.androidmvvmweatherapp.model.Weather;
import com.example.androidmvvmweatherapp.services.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel implements Callback<Weather> {

    /*@BindString(R.string.app_key)
    String mApiKey;*/

    private String mApiKey = "60157a0b8341f921509717e43ed101fc";

    private MutableLiveData<String> mCity = new MutableLiveData<>();
    private MutableLiveData<String> mDescription = new MutableLiveData<>();
    private MutableLiveData<String> mTemperature = new MutableLiveData<>();
    private MutableLiveData<String> mImageUrl = new MutableLiveData<>();
    private MutableLiveData<Integer> mErrorMessage = new MutableLiveData<>();

    public MutableLiveData<String> getCity() {
        return mCity;
    }

    /*public void setCity(MutableLiveData<String> mCity) {
        this.mCity = mCity;
    }*/

    public MutableLiveData<String> getDescription() {
        return mDescription;
    }

    public MutableLiveData<String> getTemperature() {
        return mTemperature;
    }

    public MutableLiveData<String> getImageUrl() {
        return mImageUrl;
    }

    public MutableLiveData<Integer> getErrorMessage() {
        return mErrorMessage;
    }

    public void searchCommand(View view) {
        String cityName = mCity.getValue();
        if (validateInput(cityName)) {
            WeatherService weatherService = new WeatherService();
            Call<Weather> call = weatherService.getWeatherData(cityName, mApiKey);
            call.enqueue(this);
        } else {
            mErrorMessage.setValue(R.string.input_error);
        }
    }

    private boolean validateInput(String cityName) {
        return cityName != null && cityName.length() > 2;
    }


    @Override
    public void onResponse(Call<Weather> call, Response<Weather> response) {
        if (response.isSuccessful()) {
            Weather weather = response.body();
            if (weather != null) {
                mTemperature.setValue(weather.getTemperature() + "° C");
                mDescription.setValue(weather.getDescription());
                mImageUrl.setValue(weather.getImageUrl());
            }
        } else {
            mErrorMessage.setValue(R.string.service_error);
        }
    }

    @Override
    public void onFailure(Call<Weather> call, Throwable t) {
        mErrorMessage.setValue(R.string.service_error);
    }
}