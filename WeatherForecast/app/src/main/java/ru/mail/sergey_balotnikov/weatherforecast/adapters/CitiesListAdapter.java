package ru.mail.sergey_balotnikov.weatherforecast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.mail.sergey_balotnikov.weatherforecast.R;
import ru.mail.sergey_balotnikov.weatherforecast.repositories.database.CityEntity;

public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.CityViewHolder> {

    private List<CityEntity> cityList;
    private OnItemClickListener onItemClickListener;

    public CitiesListAdapter(List<CityEntity> list, Context parent) {
        cityList=list;
        if(parent instanceof OnItemClickListener){
            onItemClickListener=(OnItemClickListener)parent;
        }
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cities_list, parent, false);
        return new CityViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        if(cityList!=null){
            CityEntity current = cityList.get(position);
            holder.cityItemView.setText(current.getName());
        } else {
            holder.cityItemView.setText("No cities. Please add city");
        }
    }

    @Override
    public int getItemCount() {
        return cityList!=null?cityList.size():0;
    }

    public void setCitiesList(List<CityEntity> list){
        cityList=list;
        notifyDataSetChanged();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView cityItemView;
        private OnItemClickListener onItemClickListener;

        CityViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            cityItemView=itemView.findViewById(R.id.itemCityNameText);
            onItemClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener!=null){
                onItemClickListener.onCityItemClick(cityItemView.getText().toString());
            }
        }
    }
    public interface OnItemClickListener{
        void onCityItemClick(String city);
    }
}
