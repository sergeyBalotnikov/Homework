package ru.mail.sergey_balotnikov.weatherforecast;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ForecastModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application application;
    public ForecastModelFactory(Application application) {
        super();
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if(modelClass==ForecastViewModel.class){
            return (T) new ForecastViewModel(application);
        }
        return null;
    }
}
