package ru.mail.sergey_balotnikov.weatherforecast;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RepositoryForecast {
    CompletableFuture<List<Forecast>> getForecastList();
    void setRepositoryForecastData(String city, boolean isCelsius);
    boolean checkIsCityValidByCityName(String city);
}
