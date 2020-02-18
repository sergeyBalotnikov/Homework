package ru.mail.sergey_balotnikov.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import ru.mail.sergey_balotnikov.weatherforecast.adapters.CitiesListAdapter;
import ru.mail.sergey_balotnikov.weatherforecast.cities.FragmentCitiesList;
import ru.mail.sergey_balotnikov.weatherforecast.forecast.FragmentCityForecast;
import ru.mail.sergey_balotnikov.weatherforecast.utils.Consts;

public class MainActivity extends AppCompatActivity implements FragmentCityForecast.CitiesListListener, CitiesListAdapter.OnItemClickListener{

    private FragmentCityForecast fragmentCityForecast;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(MODE_PRIVATE);
        fragmentCityForecast=FragmentCityForecast.newInstance();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer,
                fragmentCityForecast,
                FragmentCityForecast.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onCitiesListClick() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,
                FragmentCitiesList.getInstance(),
                FragmentCitiesList.class.getSimpleName()).commit();
    }

    @Override
    public void onCityItemClick(String city) {

        SharedPreferences preferences = null;
        try {
            preferences = getPreferences(Context.MODE_PRIVATE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        preferences.edit().putString(Consts.KEY_CITY, city).apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, FragmentCityForecast.newInstance())
                .commit();
    }
}
