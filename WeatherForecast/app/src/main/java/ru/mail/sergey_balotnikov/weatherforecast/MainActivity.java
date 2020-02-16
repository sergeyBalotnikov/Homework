package ru.mail.sergey_balotnikov.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import ru.mail.sergey_balotnikov.weatherforecast.forecast.FragmentCityForecast;

public class MainActivity extends AppCompatActivity {

    private FragmentCityForecast fragmentCityForecast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentCityForecast=FragmentCityForecast.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer,
                fragmentCityForecast,
                FragmentCityForecast.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(fragmentCityForecast).remove(fragmentCityForecast).commit();
        super.onDestroy();
    }
}
