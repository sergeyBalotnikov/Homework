package ru.mail.sergey_balotnikov.weatherforecast;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentCityForecast extends Fragment {

    public static final String LOG_TAG = "SVB";
    public static final String KEY_CITY = "Forecast City Name";
    public static final String KEY_UNITS = "Forecast City Units";
    public static FragmentCityForecast newInstance(){
        return new FragmentCityForecast();
    }

    private ForecastViewModel viewModel;
    private TextView cityName;
    private TextView cityTemperature;
    private Switch isCelsius;
    private TextView cityWeatherDescription;
    private ImageView imegeIconWeather;
    private ImageView imageListCities;
    private RecyclerView forecastRecyclerList;
    /*private String city;
    private boolean isCelsiusMetric;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_forecast, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ForecastModelFactory(
                getActivity().getApplication()))
                .get(ForecastViewModel.class);
        String city = null;
        boolean isCelsius = true;
        try {
            city = savedInstanceState.getString(KEY_CITY);
            isCelsius = savedInstanceState.getBoolean(KEY_UNITS);
        } catch (NullPointerException e) {
            e.printStackTrace();
            city="Minsk";
        }
        viewModel.setRepositoryData(city, isCelsius);
        viewModel.fetchForecastList();
        Log.d(LOG_TAG, "fetchForecastList, live data list size is "+viewModel.getForecastListLiveData().getValue().size());
        viewModel.getForecastListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Forecast>>() {
            @Override
            public void onChanged(List<Forecast> forecasts) {
                showForecast(forecasts);
            }
        });
    }

    private void showForecast(List<Forecast> forecasts) {
        updateForecast(forecasts.get(0));
        //в этом методе должен отрисовываться ui на основании полученных/обновленных данных
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityName = view.findViewById(R.id.cityName);
        cityTemperature = view.findViewById(R.id.cityTemperature);
        isCelsius = view.findViewById(R.id.swIsCelsius);
        cityWeatherDescription = view.findViewById(R.id.weatherDescription);
        imegeIconWeather = view.findViewById(R.id.imageIconWeather);
        imageListCities = view.findViewById(R.id.imageCitiesList);
        forecastRecyclerList = view.findViewById(R.id.recyclerForecastList);
        showForecast(viewModel.getForecastListLiveData().getValue());
    }

    private void setForecast(){
        OkHttpClient client = new OkHttpClient();

        final String url = String.format(
                Consts.GET_WEATHER_BY_CITY_NAME,
                "Minsk",
                !isCelsius.isActivated()?"metric":"",
                BuildConfig.API_KEY);
        System.out.println(url);
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "OkHttpCallFailed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "OkHttpCallResponse"+response.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                try {
                    List<Forecast> forecasts = new ForecastParser(response.body().string()).getParseWhether();
                    Forecast forecast = forecasts.get(0);
                    updateForecast(forecast);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void updateForecast(final Forecast forecast){
        if (forecast!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cityTemperature.setText(String.valueOf(forecast.getTemperature()));
                    cityWeatherDescription.setText(forecast.getDescription());
                }
            });
        }
    }
    private FragmentCityForecast(){}
}
