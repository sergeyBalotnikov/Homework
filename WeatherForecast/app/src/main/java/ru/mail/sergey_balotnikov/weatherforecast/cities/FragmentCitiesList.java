package ru.mail.sergey_balotnikov.weatherforecast.cities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import ru.mail.sergey_balotnikov.weatherforecast.R;
import ru.mail.sergey_balotnikov.weatherforecast.adapters.CitiesListAdapter;
import ru.mail.sergey_balotnikov.weatherforecast.utils.CheckCityValidInput;
import ru.mail.sergey_balotnikov.weatherforecast.utils.ForecastModelFactory;

public class FragmentCitiesList extends Fragment {


    public static FragmentCitiesList getInstance(){
        return new FragmentCitiesList();
    }

    private static final String LOG_TAG = "SVB";
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddCity;
    private CitiesFragmentViewModel model;
    private CitiesListAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model=new ViewModelProvider(this, new ForecastModelFactory(
                getActivity().getApplication()))
                .get(CitiesFragmentViewModel.class);
        adapter=new CitiesListAdapter(model.getLiveData().getValue(), this.getContext());

    }
    private void setAdapterList(){
        adapter.setCitiesList(model.getLiveData().getValue());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        model.getLiveData().observe(getViewLifecycleOwner(), cityEntityList ->
                FragmentCitiesList.this.setAdapterList());
        return inflater.inflate(R.layout.fragment_list_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.listCities);

        setAdapterList();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fabAddCity=view.findViewById(R.id.fabAddCity);
        fabAddCity.setOnClickListener(fab ->
            showAddCityDialog()
        );
    }

    private void showAddCityDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_add_city, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        final EditText enteredCityText =view.findViewById(R.id.etCityName);
        builder.setPositiveButton("SAVE", (dialogInterface, i) -> {
            String enteredCity = enteredCityText.getText().toString();
            CompletableFuture<Boolean> checkValidCity = CompletableFuture.supplyAsync(()->
                    CheckCityValidInput.isCityValid(enteredCity),
                    Executors.newSingleThreadExecutor());
            boolean isCityValid = false;
            try {
                checkValidCity.join();
                isCityValid = checkValidCity.get();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isCityValid){
                addCityToList(enteredCity);
                Toast.makeText(getContext(), "City is valid", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(getContext(), "City is not valid", Toast.LENGTH_LONG).show();

            }
        })
            .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void addCityToList(String cityName){
        model.addCity(cityName);
        adapter.setCitiesList(model.getLiveData().getValue());
    }

}
