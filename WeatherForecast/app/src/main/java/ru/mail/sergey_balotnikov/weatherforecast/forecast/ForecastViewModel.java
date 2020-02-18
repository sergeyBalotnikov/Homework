package ru.mail.sergey_balotnikov.weatherforecast.forecast;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import ru.mail.sergey_balotnikov.weatherforecast.repositories.RepoForecast;
import ru.mail.sergey_balotnikov.weatherforecast.repositories.RepositoryForecast;

public class ForecastViewModel extends AndroidViewModel {

    public static final String LOG_TAG = "SVB";

    private RepositoryForecast repository;
    private MutableLiveData<List<Forecast>> forecastListLiveData = new MutableLiveData<>();

    public ForecastViewModel(@NonNull Application application) {
        super(application);
        repository=new RepoForecast();
    }

    public LiveData<List<Forecast>> getForecastListLiveData() {
        return forecastListLiveData;
    }
    void fetchForecastList(){
        repository.getForecastList().thenAcceptAsync(forecasts ->
                forecastListLiveData.postValue(forecasts), Executors.newSingleThreadExecutor());
    }
    void setRepositoryData(String city, boolean isCelsius){
        repository.setRepositoryForecastData(city, isCelsius);
    }
}
