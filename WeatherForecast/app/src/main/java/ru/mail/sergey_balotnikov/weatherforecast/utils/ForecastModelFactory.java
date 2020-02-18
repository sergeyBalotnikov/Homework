package ru.mail.sergey_balotnikov.weatherforecast.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mail.sergey_balotnikov.weatherforecast.cities.CitiesFragmentViewModel;
import ru.mail.sergey_balotnikov.weatherforecast.forecast.ForecastViewModel;

public class ForecastModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application application;
    public ForecastModelFactory(Application application) {
        super();
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if(modelClass== ForecastViewModel.class){
            return (T) new ForecastViewModel(application);
        }
        if(modelClass== CitiesFragmentViewModel.class){
            return (T) new CitiesFragmentViewModel(application);
        }
        return null;
    }
}
