package in.ascentrasolutions.krishi.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.HalfPriceAdapter;
import in.ascentrasolutions.krishi.Fetchers.HalfPriceFetcher;
import in.ascentrasolutions.krishi.Getters.HalfPrice;
import in.ascentrasolutions.krishi.R;


public class HalfPriceFragment extends Fragment {

    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_half_price, container, false);

        try {
            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }



        RecyclerView recyclerView = view.findViewById(R.id.halfPriceRecycler);
        ArrayList<HalfPrice> halfPrices = new ArrayList<>();
        HalfPriceAdapter adapter = new HalfPriceAdapter(requireContext(), halfPrices);


        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        HalfPriceFetcher halfPriceFetcher = new HalfPriceFetcher(halfPrices, adapter);
        halfPriceFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/yield");





        return view;
    }
}