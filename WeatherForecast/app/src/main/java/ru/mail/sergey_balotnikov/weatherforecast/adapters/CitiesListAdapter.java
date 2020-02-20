package ru.mail.sergey_balotnikov.weatherforecast.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.mail.sergey_balotnikov.weatherforecast.R;
import ru.mail.sergey_balotnikov.weatherforecast.repositories.database.CityEntity;

public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.CityViewHolder> {

    private List<CityEntity> cityList = Collections.emptyList();
    private OnItemClickListener onItemClickListener;

    public CitiesListAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cities_list, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.bind(cityList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityList!=null?cityList.size():0;
    }

    public void setCitiesList(List<CityEntity> list){
        cityList=list;
        Log.d("SVB", "CitiesListAdapter#setCitiesList" + list.size());
        notifyDataSetChanged();
    }
    public void addCityToList(String city){
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(city);
        ArrayList<CityEntity> cityEntities = (ArrayList<CityEntity>) cityList;
        cityEntities.add(cityEntity);
        setCitiesList(cityEntities);
    }

    public List<CityEntity> getCitiesList() {
        return cityList;
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        private final TextView cityItemView;

        CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityItemView=itemView.findViewById(R.id.itemCityNameText);
        }
        private void bind(final CityEntity cityEntity) {
            cityItemView.setText(cityEntity.getName());
            Log.d("CitiesListAdapter", "CitiesListAdapter#bind");
            itemView.setOnClickListener(view -> {
                Log.d("CitiesListAdapter", "CitiesListAdapter#Click");
                if (onItemClickListener != null) {
                    onItemClickListener.onCityItemClick(cityEntity.getName());
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onCityItemClick(String city);
    }
}
