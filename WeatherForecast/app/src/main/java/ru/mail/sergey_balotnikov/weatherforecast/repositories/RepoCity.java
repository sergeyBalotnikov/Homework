package ru.mail.sergey_balotnikov.weatherforecast.repositories;

import android.app.Application;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.mail.sergey_balotnikov.weatherforecast.repositories.database.CitiesDatabase;
import ru.mail.sergey_balotnikov.weatherforecast.repositories.database.CityDao;
import ru.mail.sergey_balotnikov.weatherforecast.repositories.database.CityEntity;

public class RepoCity implements RepositoryCity {

    private final ExecutorService EXECUTOR = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    private CityDao cityDao;

    public RepoCity(Application application) {
        cityDao = CitiesDatabase.getInstance(application).cityDao();
    }

    @Override
    public CompletableFuture<List<CityEntity>> getAllCities() {
        return CompletableFuture.supplyAsync(()->cityDao.getAllCities(), EXECUTOR);
    }

    @Override
    public CompletableFuture<CityEntity> getCityById(long id) {
        return CompletableFuture.supplyAsync(()->cityDao.getCityById(id), EXECUTOR);
    }

    @Override
    public CompletableFuture<Void> addCity(String name) {
        CityEntity entity = new CityEntity();
        entity.setName(name);
        return CompletableFuture.supplyAsync(()->{
                cityDao.addCity(entity);
                return null;
                },
                EXECUTOR
                );
    }

    @Override
    public CompletableFuture<Void> deleteAll() {
        return CompletableFuture.supplyAsync(()->{
            cityDao.deleteAll();
            return null;
            },
                EXECUTOR);
    }
}
