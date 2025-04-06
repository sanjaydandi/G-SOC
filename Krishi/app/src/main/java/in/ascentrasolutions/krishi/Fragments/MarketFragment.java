package in.ascentrasolutions.krishi.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.HalfPriceAdapter;
import in.ascentrasolutions.krishi.Fetchers.GraphFetcher;
import in.ascentrasolutions.krishi.Fetchers.HalfPriceFetcher;
import in.ascentrasolutions.krishi.Getters.HalfPrice;
import in.ascentrasolutions.krishi.R;


public class MarketFragment extends Fragment {

    LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        try {
            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }

        RecyclerView recyclerView = view.findViewById(R.id.market_recycler);
        ArrayList<HalfPrice> list = new ArrayList<>();
        HalfPriceAdapter adapter = new HalfPriceAdapter(requireContext(), list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        HalfPriceFetcher halfPriceFetcher = new HalfPriceFetcher(list, adapter);
        halfPriceFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/yield");


        lineChart = view.findViewById(R.id.lineChart);

        GraphFetcher graphFetcher = new GraphFetcher(lineChart);
        graphFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/graphs/graph");

        setupLineChart();
        return view;

    }

    private void setupLineChart() {
    }



}