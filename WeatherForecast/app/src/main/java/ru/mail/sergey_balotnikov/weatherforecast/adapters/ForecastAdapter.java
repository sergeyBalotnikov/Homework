package ru.mail.sergey_balotnikov.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ru.mail.sergey_balotnikov.weatherforecast.R;
import ru.mail.sergey_balotnikov.weatherforecast.forecast.Forecast;
import ru.mail.sergey_balotnikov.weatherforecast.utils.Consts;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.WhetherDetailsHolder> {

    private List<Forecast> forecastList;

    public void setForecastAdapterList(List<Forecast> forecastList) {
        this.forecastList = forecastList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WhetherDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_whether_details_for_days, parent, false);
        return new WhetherDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WhetherDetailsHolder holder, int position) {
        if(forecastList !=null&&!forecastList.isEmpty()){
            Forecast currentWhether = forecastList.get(position+1);
            holder.description.setText(currentWhether.getDescription());
            holder.temperature.setText(String.valueOf(currentWhether.getTemperature()));
            String time = new SimpleDateFormat("EE 'в' h a")
                    .format(new Date(currentWhether.getTime()*1000));
            holder.time.setText(time);
            holder.showIcon(currentWhether.getIconId());
        } else {
            holder.description.setText("city");
        }
    }

    @Override
    public int getItemCount() {
        return forecastList !=null? forecastList.size()-1:0;
    }

    public class WhetherDetailsHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView time;
        private final TextView temperature;
        private final TextView description;

        public WhetherDetailsHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.imageWhetherIcon);
            time=itemView.findViewById(R.id.tvDateTime);
            temperature = itemView.findViewById(R.id.tvTemperature);
            description = itemView.findViewById(R.id.tvDescription);
        }
        private void showIcon(String iconId){
            String imageUrl = String.format(Consts.GET_ICON, iconId);
            Picasso.get().load(imageUrl).into(icon);
        }
    }
}
