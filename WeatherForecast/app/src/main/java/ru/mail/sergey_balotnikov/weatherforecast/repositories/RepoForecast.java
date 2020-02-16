package ru.mail.sergey_balotnikov.weatherforecast.repositories;

import android.util.Log;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import okhttp3.Call;
import okhttp3.Callback;
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
    private boolean isCityValid;

    @Override
    public CompletableFuture<List<Forecast>> getForecastList() {
        return CompletableFuture.supplyAsync(new Supplier<List<Forecast>>() {
            @Override
            public List<Forecast> get() {
                return getForecastListByCity(city, isCelsius);
            }
        }, EXECUTOR);
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
            List<Forecast> forecasts = new ForecastParser(response.body().string()).getParseWhether();
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

    @Override
    public synchronized boolean checkIsCityValidByCityName(String city) {
        return isCityValid(city);
    }
    private boolean isCityValid(String city){
        OkHttpClient client = new OkHttpClient();
        final String url = String.format(
                Consts.GET_WEATHER_BY_CITY_NAME,
                city,
                "",
                BuildConfig.API_KEY);
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(LOG_TAG, "Request call onFailure"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String jsonCod = jsonObject.get("cod").toString();
                    if(jsonCod.equals("400")||jsonCod.equals("404")){
                        setCityValid(false);
                    } else {
                        setCityValid(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
        return isCityValid;
    }

    public void setCityValid(boolean cityValid) {
        isCityValid = cityValid;
    }
}
