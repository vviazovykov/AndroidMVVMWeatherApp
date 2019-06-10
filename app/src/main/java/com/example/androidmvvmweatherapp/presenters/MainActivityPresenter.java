package com.example.androidmvvmweatherapp.presenters;

public class MainActivityPresenter {
    private View mView;

    public MainActivityPresenter(View view) {
        this.mView = view;
    }

    public void searchCommand(String value) throws Exception {
        if (mView.validateInput(value)) {
            mView.performSearch(value);
        } else {
            throw new Exception();
        }
    }

    public interface View {
        boolean validateInput(String value);
        void performSearch(String value);
        void updateTemperature(int value);
        void updateDescription(String value);
        void updateImage(String value);
        void displayServiceErrorMessage();
    }
}
