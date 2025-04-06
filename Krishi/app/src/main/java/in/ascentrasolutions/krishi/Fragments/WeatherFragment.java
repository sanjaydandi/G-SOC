package in.ascentrasolutions.krishi.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.WeatherAdapter;
import in.ascentrasolutions.krishi.Fetchers.CityFetcher;
import in.ascentrasolutions.krishi.Fetchers.WeatherFetcher;
import in.ascentrasolutions.krishi.Getters.Weather;
import in.ascentrasolutions.krishi.R;


public class WeatherFragment extends Fragment {

    final double[] latitude = new double[1];
    final double[] longitude = new double[1];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        try {
            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }


        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());








        RecyclerView recyclerView = view.findViewById(R.id.weather_recycler);
        ArrayList<Weather> list = new ArrayList<>();
        WeatherAdapter adapter = new WeatherAdapter(list, requireContext());


        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        TextView location1 = view.findViewById(R.id.location);
        TextView temp_c = view.findViewById(R.id.temp);
        ImageView icon = view.findViewById(R.id.SImageView);


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        latitude[0] = location.getLatitude();
                        longitude[0] = location.getLongitude();
                        Log.d("Location", "Lat: " + latitude[0] + ", Lon: " + longitude[0]);

                        CityFetcher cityFetcher = new CityFetcher(requireContext(), list, location1, temp_c, icon, adapter);
                        cityFetcher.fetchData("https://nominatim.openstreetmap.org/reverse?format=json&lat=" + latitude[0] +"&lon=" + longitude[0]);


                    } else {
                        Log.d("Location", "Location is null");
                    }
                });




        return view;
    }
}