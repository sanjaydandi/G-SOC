package in.ascentrasolutions.krishi.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Getters.WeatherHour;
import in.ascentrasolutions.krishi.R;

public class WeatherHourAdapter extends RecyclerView.Adapter<WeatherHourAdapter.ViewHolder>{

    private final ArrayList<WeatherHour> weatherHourList;
    private final Context context;

    public WeatherHourAdapter(ArrayList<WeatherHour> weatherHourList, Context context) {
        this.weatherHourList = weatherHourList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weatherhour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherHour list = weatherHourList.get(position);

        holder.time.setText(list.getTime());
        holder.windSpeed.setText(list.getWindKmp());
        holder.windChill.setText(list.getWindChill_c());
        holder.humidity.setText(list.getHumidity());
        holder.dewPoint.setText(list.getDewPoint_c());
        holder.temperature.setText(list.getTemp_c());
        holder.heat.setText(list.getHeat_c());
        holder.cloud.setText(list.getCloud());

        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load("https:" + list.getIcon())
                .error(R.drawable.app_logo)
                .into(holder.icon);

        Log.e("imageFet", list.getIcon());

    }

    @Override
    public int getItemCount() {
        return weatherHourList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView time,  windChill, humidity, dewPoint, heat, temperature, windSpeed, cloud;
        private final ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.timeText);
            windChill = itemView.findViewById(R.id.windChillText);
            humidity = itemView.findViewById(R.id.humidityText);
            dewPoint = itemView.findViewById(R.id.dewPointText);
            heat = itemView.findViewById(R.id.heatText);
            temperature = itemView.findViewById(R.id.temp_cText);
            windSpeed = itemView.findViewById(R.id.windKphText);
            cloud = itemView.findViewById(R.id.cloudText);
            //rain = itemView.findViewById(R.id.rainText);

            icon = itemView.findViewById(R.id.WeatherHourImageView);


        }
    }
}
