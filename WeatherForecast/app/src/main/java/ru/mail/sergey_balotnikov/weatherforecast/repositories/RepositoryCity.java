package ru.mail.sergey_balotnikov.weatherforecast.repositories;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import ru.mail.sergey_balotnikov.weatherforecast.repositories.database.CityEntity;


public interface RepositoryCity {
    CompletableFuture <List<CityEntity>> getAllCities();
    CompletableFuture <CityEntity> getCityById(long id);
    CompletableFuture <Void> addCity(String name);
    CompletableFuture<Void> deleteAll();
}
