package ru.mail.sergey_balotnikov.weatherforecast.cities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;import android.widget.TextView;
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
import ru.mail.sergey_balotnikov.weatherforecast.utils.ViewModelFactory;

public class FragmentCitiesList extends Fragment {


    private static final String LOG_TAG = "SVB";
    public static FragmentCitiesList getInstance(){
        return new FragmentCitiesList();
    }

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddCity;
    private CitiesFragmentViewModel model;
    private TextView emptyListMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        model=new ViewModelProvider(this, viewModelFactory).get(CitiesFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        model.getLiveData().observe(getViewLifecycleOwner(), cityEntityList -> setAdapterList());
        return inflater.inflate(R.layout.fragment_list_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyListMessage = view.findViewById(R.id.emptyCitiesListMessage);

        recyclerView=view.findViewById(R.id.listCities);
        recyclerView.setAdapter(new CitiesListAdapter(
                (CitiesListAdapter.OnItemClickListener)this.getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        fabAddCity=view.findViewById(R.id.fabAddCity);
        fabAddCity.setOnClickListener(fab -> showAddCityDialog());
        model.fetchCitiesList();
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
            } else{
                Toast.makeText(getContext(), "City is not valid", Toast.LENGTH_LONG).show();

            }
        })
            .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addCityToList(String cityName) {
        model.addCity(cityName);
        Log.d(LOG_TAG, "Add city "+cityName);
        if (recyclerView.getAdapter() != null) {
            ((CitiesListAdapter) recyclerView.getAdapter()).addCityToList(cityName);
            ((CitiesListAdapter) recyclerView.getAdapter()).setCitiesList(model.getLiveData().getValue());
        }
        setAdapterList();
    }

    private void setAdapterList() {
        if (recyclerView.getAdapter() != null) {
            ((CitiesListAdapter) recyclerView.getAdapter()).setCitiesList(model.getLiveData().getValue());
            if(recyclerView.getAdapter().getItemCount()==0){
                emptyListMessage.setVisibility(View.VISIBLE);
            } else {
                emptyListMessage.setVisibility(View.INVISIBLE);
            }
        }
    }
}
