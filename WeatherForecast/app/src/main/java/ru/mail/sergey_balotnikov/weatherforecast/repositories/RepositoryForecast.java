package ru.mail.sergey_balotnikov.weatherforecast.repositories;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import ru.mail.sergey_balotnikov.weatherforecast.forecast.Forecast;

public interface RepositoryForecast {
    CompletableFuture<List<Forecast>> getForecastList();
    void setRepositoryForecastData(String city, boolean isCelsius);
    boolean checkIsCityValidByCityName(String city);
}
