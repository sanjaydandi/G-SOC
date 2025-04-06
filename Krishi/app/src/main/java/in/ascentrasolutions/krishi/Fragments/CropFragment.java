package in.ascentrasolutions.krishi.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.SoilAdapter;
import in.ascentrasolutions.krishi.Fetchers.CropFetcher;
import in.ascentrasolutions.krishi.Getters.Soils;
import in.ascentrasolutions.krishi.R;


public class CropFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);


        RecyclerView recyclerView = view.findViewById(R.id.crop_recycler);
        ArrayList<Soils> soils = new ArrayList<>();
        SoilAdapter soilAdapter = new SoilAdapter(requireContext(), soils);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(soilAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        CropFetcher cropFetcher = new CropFetcher(soilAdapter, soils, "crop_name", "crop_image");
        cropFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/crops");


        RecyclerView recyclerView1 = view.findViewById(R.id.soil_recycler);
        ArrayList<Soils> soils1 = new ArrayList<>();
        SoilAdapter soilAdapter1 = new SoilAdapter(requireContext(), soils1);

        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(soilAdapter1);
        recyclerView1.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        CropFetcher cropFetcher1 = new CropFetcher(soilAdapter1, soils1, "soil_name", "soil_image");
        cropFetcher1.fetchData("https://www.ascentrasolutions.in/apps/krishi/soil/soil");

        return view;
    }
}