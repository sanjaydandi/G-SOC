package in.ascentrasolutions.krishi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

import in.ascentrasolutions.krishi.Getters.Weather;
import in.ascentrasolutions.krishi.R;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{

    private final ArrayList<Weather> list;
    private final Context context;

    public WeatherAdapter(ArrayList<Weather> list, Context context) {
        this.list = list;
        this.context = context;
    }





    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {

        Weather weatherList = list.get(position);

        holder.itRain.setText(weatherList.getItRain());
        holder.maxTemp.setText(weatherList.getMaxTemp_c());
        holder.minTemp.setText(weatherList.getMinTemp_c());
        holder.avgTemp.setText(weatherList.getAvgTemp_c());
        holder.windSpeed.setText(weatherList.getMaxWind_kph());

        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.app_logo)
                .load("https:" + weatherList.getImage())
                .into(holder.image);

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        WeatherHourAdapter adapter = new WeatherHourAdapter(weatherList.getWeatherHourList(), context);
        holder.recyclerView.setAdapter(adapter);

        holder.itemView.setOnClickListener(v -> {

            if(holder.recyclerView.getVisibility() != View.VISIBLE) {

                holder.recyclerView.setVisibility(View.VISIBLE);
            } else {

                holder.recyclerView.setVisibility(View.GONE);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView maxTemp, minTemp, avgTemp, itRain, windSpeed;
        private final RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            maxTemp = itemView.findViewById(R.id.maxTempText);
            minTemp = itemView.findViewById(R.id.minTempText);
            avgTemp = itemView.findViewById(R.id.avgTempText);
            windSpeed = itemView.findViewById(R.id.windText);
            itRain = itemView.findViewById(R.id.rainText);

            image = itemView.findViewById(R.id.weatherImage);

            recyclerView = itemView.findViewById(R.id.inner_recycler_weather);

        }
    }
}
