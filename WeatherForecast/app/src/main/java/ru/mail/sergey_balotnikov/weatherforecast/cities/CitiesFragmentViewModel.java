package ru.mail.sergey_balotnikov.weatherforecast.cities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.mail.sergey_balotnikov.weatherforecast.repositories.RepoCity;
import ru.mail.sergey_balotnikov.weatherforecast.repositories.RepositoryCity;
import ru.mail.sergey_balotnikov.weatherforecast.repositories.database.CityEntity;


public class CitiesFragmentViewModel extends AndroidViewModel {

    private RepositoryCity repository;
    private MutableLiveData<List<CityEntity>> liveData = new MutableLiveData<>();

    public CitiesFragmentViewModel(@NonNull Application application) {
        super(application);
        repository=new RepoCity(application);
    }

    public MutableLiveData<List<CityEntity>> getLiveData() {
        fetchCitiesList();
        return liveData;
    }

    void fetchCitiesList(){
        repository.getAllCities().thenAccept(listCities->liveData.postValue(listCities));
    }

    public void addCity(String cityName) {
        repository.addCity(cityName);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
}
