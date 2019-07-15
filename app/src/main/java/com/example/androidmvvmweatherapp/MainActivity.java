package com.example.androidmvvmweatherapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidmvvmweatherapp.databinding.ActivityMainBinding;
import com.example.androidmvvmweatherapp.viewmodels.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.temperatureTextView)
    TextView mTemperatureTextView;
    @BindView(R.id.descriptionTextView)
    TextView mDescriptionTextView;
    @BindView(R.id.weatherImageView)
    ImageView mImageView;

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.setViewmodel(mMainViewModel);

        setContentView(binding.getRoot());

        ButterKnife.bind(this);

        final Observer<String> temperatureObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mTemperatureTextView.setText(s);
            }
        };
        mMainViewModel.getTemperature().observe(this, temperatureObserver);

        final Observer<String> descriptionObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mDescriptionTextView.setText(s);
            }
        };
        mMainViewModel.getDescription().observe(this, descriptionObserver);

        final Observer<String> imageUrlObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Glide.with(MainActivity.this).load(s).into(mImageView);
            }
        };
        mMainViewModel.getImageUrl().observe(this, imageUrlObserver);

        final Observer<Integer> errorMessageObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Toast.makeText(MainActivity.this, integer, Toast.LENGTH_SHORT).show();
            }
        };
        mMainViewModel.getErrorMessage().observe(this, errorMessageObserver);
    }
}