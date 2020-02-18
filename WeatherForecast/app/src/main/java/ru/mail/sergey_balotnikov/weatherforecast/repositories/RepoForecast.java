package ru.mail.sergey_balotnikov.weatherforecast.repositories;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.mail.sergey_balotnikov.weatherforecast.BuildConfig;
import ru.mail.sergey_balotnikov.weatherforecast.forecast.Forecast;
import ru.mail.sergey_balotnikov.weatherforecast.utils.Consts;
import ru.mail.sergey_balotnikov.weatherforecast.utils.ForecastParser;

public class RepoForecast implements RepositoryForecast {

    public static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() - 1);
    public static final String LOG_TAG = "SVB";

    private String city;
    private boolean isCelsius;
    private List<Forecast> forecastList;
    /*private static boolean isCityValid;*/

    @Override
    public CompletableFuture<List<Forecast>> getForecastList() {
        return CompletableFuture.supplyAsync(() ->
                getForecastListByCity(city, isCelsius), EXECUTOR);
    }
    private List<Forecast> getForecastListByCity(String city, boolean isCelsius){

        OkHttpClient client = new OkHttpClient();
        final String url = String.format(
                Consts.GET_WEATHER_BY_CITY_NAME,
                city,
                !isCelsius?"metric":"imperial",
                BuildConfig.API_KEY);
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            List<Forecast> forecasts = new ForecastParser(
                    response.body().string()).getParseWhether();
            setForecastList(forecasts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forecastList;
    }

    public void setForecastList(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    @Override
    public void setRepositoryForecastData(String city, boolean isCelsius) {
        setCity(city);
        setCelsius(isCelsius);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCelsius(boolean celsius) {
        isCelsius = celsius;
    }
}
