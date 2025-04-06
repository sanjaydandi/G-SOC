package in.ascentrasolutions.krishi.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.InteractorAdapter;
import in.ascentrasolutions.krishi.Adapters.VendorsAdapter;
import in.ascentrasolutions.krishi.Fetchers.FarmerAndVendor;
import in.ascentrasolutions.krishi.Getters.Interactors;
import in.ascentrasolutions.krishi.Getters.Vendors;
import in.ascentrasolutions.krishi.R;


public class VendorsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendors, container, false);
        try {
            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }

        ConstraintLayout vendor_layout = view.findViewById(R.id.vendor_layout);
        ConstraintLayout farmer_layout = view.findViewById(R.id.farmer_layout);

        Button farmer_but = view.findViewById(R.id.farmer_button);
        Button vendor_but = view.findViewById(R.id.vendor_button);

        farmer_but.setBackgroundResource(R.drawable.medium_sea_green_border);



        farmer_but.setOnClickListener(v -> {
            farmer_layout.setVisibility(View.VISIBLE);
            vendor_layout.setVisibility(View.GONE);
            farmer_but.setBackgroundResource(R.drawable.medium_sea_green_border);

            vendor_but.setBackgroundResource(R.drawable.border);
        });

        vendor_but.setOnClickListener(v -> {
            farmer_layout.setVisibility(View.GONE);
            vendor_layout.setVisibility(View.VISIBLE);


            vendor_but.setBackgroundResource(R.drawable.medium_sea_green_border);

            farmer_but.setBackgroundResource(R.drawable.border);
        });


        RecyclerView recyclerView = view.findViewById(R.id.vendor_recycler);
        ArrayList<Interactors> interactors = new ArrayList<>();
        VendorsAdapter vendorsAdapter = new VendorsAdapter(interactors, requireContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(vendorsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        RecyclerView recyclerView1 = view.findViewById(R.id.farmer_recycler);
        ArrayList<Interactors> interactors1 = new ArrayList<>();
        VendorsAdapter vendorsAdapter1 = new VendorsAdapter(interactors1, requireContext());

        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(vendorsAdapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(requireContext()));


        FarmerAndVendor farmerAndVendor1 = new FarmerAndVendor(vendorsAdapter1, interactors1);
        farmerAndVendor1.fetchData("https://www.ascentrasolutions.in/apps/krishi/companies/farmer");


        FarmerAndVendor farmerAndVendor = new FarmerAndVendor(vendorsAdapter, interactors);
        farmerAndVendor.fetchData("https://www.ascentrasolutions.in/apps/krishi/companies/vendor");



        return view;
    }
}