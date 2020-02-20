package ru.mail.sergey_balotnikov.weatherforecast.forecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import ru.mail.sergey_balotnikov.weatherforecast.R;
import ru.mail.sergey_balotnikov.weatherforecast.adapters.ForecastAdapter;
import ru.mail.sergey_balotnikov.weatherforecast.utils.Consts;
import ru.mail.sergey_balotnikov.weatherforecast.utils.ViewModelFactory;

public class FragmentCityForecast extends Fragment {

    public static final String LOG_TAG = "SVB";
    public static FragmentCityForecast newInstance(){
        return new FragmentCityForecast();
    }
    private CitiesListListener listListener;
    private SharedPreferences sharedPreferences;
    private ForecastAdapter adapter;
    private ForecastViewModel viewModel;
    private TextView cityName;
    private TextView cityTemperature;
    private Switch isCelsiusSwitch;
    private TextView cityWeatherDescription;
    private ImageView imageIconWeather;
    private ImageView imageListCities;
    private RecyclerView forecastRecyclerList;
    private String city;
    private boolean isCelsius;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(LOG_TAG, "CityForecastAttach");
        if(context instanceof CitiesListListener){
            listListener=(CitiesListListener)context;
        }
        viewModel = new ViewModelProvider(this, new ViewModelFactory(
                getActivity().getApplication()))
                .get(ForecastViewModel.class);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Log.d(LOG_TAG, sharedPreferences.getString(Consts.KEY_CITY, "Minsk"));
        city = sharedPreferences.getString(Consts.KEY_CITY, "Minsk");
        isCelsius = sharedPreferences.getBoolean(Consts.KEY_UNITS, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setRepositoryData(city, isCelsius);
        viewModel.fetchForecastList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_forecast, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            viewModel.getForecastListLiveData().observe(getViewLifecycleOwner(), forecasts ->
                    showForecast(forecasts));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
        }
    }

    private void showForecast(List<Forecast> forecasts) {
        updateForecast(forecasts.get(0));
        adapter.setForecastAdapterList(forecasts);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forecastRecyclerList = view.findViewById(R.id.recyclerForecastList);
        adapter = new ForecastAdapter();
        adapter.setForecastAdapterList(viewModel.getForecastListLiveData().getValue());
        forecastRecyclerList.setAdapter(adapter);
        forecastRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        cityName = view.findViewById(R.id.cityName);
        cityName.setText(city);
        cityTemperature = view.findViewById(R.id.cityTemperature);

        isCelsiusSwitch = view.findViewById(R.id.swIsCelsius);
        isCelsiusSwitch.setChecked(isCelsius);
        isCelsiusSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            isCelsius=b;
            viewModel.setRepositoryData(city, isCelsius);
            viewModel.fetchForecastList();
        });

        cityWeatherDescription = view.findViewById(R.id.weatherDescription);
        imageIconWeather = view.findViewById(R.id.imageIconWeather);
        imageListCities = view.findViewById(R.id.imageCitiesList);
        imageListCities.setOnClickListener(view1 -> openCitiesList());
        forecastRecyclerList = view.findViewById(R.id.recyclerForecastList);
        try {
            showForecast(viewModel.getForecastListLiveData().getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCitiesList() {
        listListener.onCitiesListClick();
    }

    private void updateForecast(final Forecast forecast){
        if (forecast!=null){
            getActivity().runOnUiThread(() -> {
                cityTemperature.setText(String.valueOf(forecast.getTemperature()));
                cityWeatherDescription.setText(forecast.getDescription());
                String iconUrl = String.format(Consts.GET_ICON, forecast.getIconId());
                Picasso.get().load(iconUrl).into(imageIconWeather);
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        sharedPreferences.edit().putBoolean(Consts.KEY_UNITS, isCelsius).apply();
        sharedPreferences.edit().putString(Consts.KEY_CITY, city).apply();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        Bundle bundle = new Bundle();
        onSaveInstanceState(bundle);
        super.onDestroyView();
    }
    public interface CitiesListListener{
        void onCitiesListClick();
    }
}
