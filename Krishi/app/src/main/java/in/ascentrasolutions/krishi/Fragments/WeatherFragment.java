package in.ascentrasolutions.krishi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.WeatherAdapter;
import in.ascentrasolutions.krishi.Fetchers.WeatherFetcher;
import in.ascentrasolutions.krishi.Getters.Weather;
import in.ascentrasolutions.krishi.R;


public class WeatherFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.weather_recycler);
        ArrayList<Weather> list = new ArrayList<>();
        WeatherAdapter adapter = new WeatherAdapter(list, requireContext());


        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        TextView location = view.findViewById(R.id.location);
        TextView temp_c = view.findViewById(R.id.temp);
        ImageView icon = view.findViewById(R.id.SImageView);

        WeatherFetcher fetch = new WeatherFetcher(list, adapter, location, temp_c, icon, requireContext());
        fetch.fetchData("https://api.weatherapi.com/v1/forecast.json?key=e2b3d6452ff94726815100645250103&q=Ongole&days=7&aqi=no&alerts=no");
        return view;
    }
}