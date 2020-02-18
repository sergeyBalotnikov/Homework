package ru.mail.sergey_balotnikov.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ru.mail.sergey_balotnikov.weatherforecast.adapters.CitiesListAdapter;
import ru.mail.sergey_balotnikov.weatherforecast.cities.FragmentCitiesList;
import ru.mail.sergey_balotnikov.weatherforecast.forecast.FragmentCityForecast;
import ru.mail.sergey_balotnikov.weatherforecast.utils.Consts;

public class MainActivity extends AppCompatActivity implements FragmentCityForecast.CitiesListListener, CitiesListAdapter.OnItemClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,
                FragmentCityForecast.newInstance(),
                FragmentCityForecast.class.getSimpleName())
                .commit();
    }

    @Override
    public void onCitiesListClick() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,
                FragmentCitiesList.getInstance(),
                FragmentCitiesList.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCityItemClick(String city) {
        getPreferences(MODE_PRIVATE).edit().putString(Consts.KEY_CITY, city).apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, FragmentCityForecast.newInstance())
                .commit();
        Toast.makeText(this, "Работай, бля!", Toast.LENGTH_LONG).show();

    }
}
