package com.example.androidmvvmweatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidmvvmweatherapp.presenters.MainActivityPresenter;
import com.example.androidmvvmweatherapp.services.WeatherServiceAsyncTask;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    @BindView(R.id.cityEditText)
    EditText mCityEditText;
    @BindView(R.id.temperatureTextView)
    TextView mTemperatureTextView;
    @BindView(R.id.descriptionTextView)
    TextView mDescriptionTextView;
    @BindView(R.id.weatherImageView)
    ImageView mImageView;

    private MainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPresenter = new MainActivityPresenter(this);
    }

    @OnClick(R.id.searchButton)
    public void searchClickHandler(View view) {

        String city = mCityEditText.getText().toString();
        try {
            mPresenter.searchCommand(city);
        } catch (Exception e) {
            Toast.makeText(this, R.string.input_error, Toast.LENGTH_SHORT).show();
        }
     }

    @Override
    public boolean validateInput(String value) {
        return value != null && value.length() > 2;
    }

    @Override
    public void performSearch(String value) {
        WeatherServiceAsyncTask weatherServiceAsyncTask = new WeatherServiceAsyncTask(this);
        weatherServiceAsyncTask.execute(value);
    }

    @Override
    public void updateTemperature(int value) {
        mTemperatureTextView.setText(String.format(Locale.getDefault(), "%d° C", value));
    }

    @Override
    public void updateDescription(String value) {
        mDescriptionTextView.setText(value);
    }

    @Override
    public void updateImage(String imageUrl) {
        Glide.with(this).load(imageUrl).into(mImageView);
    }

    @Override
    public void displayServiceErrorMessage() {
        Toast.makeText(this, R.string.service_error, Toast.LENGTH_SHORT).show();
    }
}
