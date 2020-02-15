package ru.mail.sergey_balotnikov.weatherforecast;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

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
    private Switch isCelsiusSwitch;
    private TextView cityWeatherDescription;
    private ImageView imegeIconWeather;
    private ImageView imageListCities;
    private RecyclerView forecastRecyclerList;
    private String city;
    private boolean isCelsius;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this, new ForecastModelFactory(
                getActivity().getApplication()))
                .get(ForecastViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        city = null;
        isCelsius = true;
        try {
            city = savedInstanceState.getString(KEY_CITY);
            isCelsius = savedInstanceState.getBoolean(KEY_UNITS);
        } catch (NullPointerException e) {
            e.printStackTrace();
            city="Minsk";
        }
        viewModel.setRepositoryData(city, isCelsius);
        viewModel.fetchForecastList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_forecast, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            viewModel.getForecastListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Forecast>>() {
                @Override
                public void onChanged(List<Forecast> forecasts) {
                    showForecast(forecasts);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
        }
    }

    private void showForecast(List<Forecast> forecasts) {
        updateForecast(forecasts.get(0));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityName = view.findViewById(R.id.cityName);
        cityName.setText(city);
        cityTemperature = view.findViewById(R.id.cityTemperature);
        isCelsiusSwitch = view.findViewById(R.id.swIsCelsius);
        isCelsiusSwitch.setChecked(isCelsius);
        isCelsiusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCelsius=b;
                viewModel.setRepositoryData(city, isCelsius);
                viewModel.fetchForecastList();
            }
        });
        cityWeatherDescription = view.findViewById(R.id.weatherDescription);
        imegeIconWeather = view.findViewById(R.id.imageIconWeather);
        imageListCities = view.findViewById(R.id.imageCitiesList);
        forecastRecyclerList = view.findViewById(R.id.recyclerForecastList);
        try {
            showForecast(viewModel.getForecastListLiveData().getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateForecast(final Forecast forecast){
        if (forecast!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cityTemperature.setText(String.valueOf(forecast.getTemperature()));
                    cityWeatherDescription.setText(forecast.getDescription());

                    String iconUrl = String.format(Consts.GET_ICON, forecast.getIconId());
                    Picasso.get().load(iconUrl).into(imegeIconWeather);
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_CITY, city);
        outState.putBoolean(KEY_UNITS, isCelsius);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            city=savedInstanceState.getString(KEY_CITY);
            isCelsius=savedInstanceState.getBoolean(KEY_UNITS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FragmentCityForecast(){}
}
