package in.ascentrasolutions.krishi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.SignYieldAdapter;
import in.ascentrasolutions.krishi.Fetchers.YieldFetcher;
import in.ascentrasolutions.krishi.Getters.Yield;
import in.ascentrasolutions.krishi.R;


public class YieldFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yield, container, false);

        RecyclerView bestRecycler, desiredRecycler, mediumRecycler, wasteRecycler;
        ArrayList<Yield> list, list1, list2;
        SignYieldAdapter adapter, adapter1, adapter2;

        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        bestRecycler = view.findViewById(R.id.bestCropsRecycler);
        mediumRecycler = view.findViewById(R.id.mediumCropsRecycler);
        wasteRecycler = view.findViewById(R.id.waste_recycler);

        adapter = new SignYieldAdapter(list, requireContext());
        adapter1 = new SignYieldAdapter(list1, requireContext());
        adapter2 = new SignYieldAdapter(list2, requireContext());



        bestRecycler.setHasFixedSize(true);
        bestRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        bestRecycler.setAdapter(adapter);

      /*  desiredRecycler.setHasFixedSize(true);
        desiredRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        desiredRecycler.setAdapter(adapter1); */
        mediumRecycler.setHasFixedSize(true);
        mediumRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        mediumRecycler.setAdapter(adapter1);

        wasteRecycler.setHasFixedSize(true);
        wasteRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        wasteRecycler.setAdapter(adapter2);

        YieldFetcher yieldFetcher = new YieldFetcher(list, adapter);
        yieldFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/bestyield");


        YieldFetcher yieldFetcher1 = new YieldFetcher(list1, adapter1);
        yieldFetcher1.fetchData("\"https://www.ascentrasolutions.in/apps/krishi/yield\"");


        YieldFetcher yieldFetcher2 = new YieldFetcher(list2, adapter2);
        yieldFetcher2.fetchData( "https://www.ascentrasolutions.in/apps/krishi/wasteyield");


        return view;
    }
}