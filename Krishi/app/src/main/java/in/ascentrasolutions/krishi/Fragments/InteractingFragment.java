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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.InteractingAdapter;
import in.ascentrasolutions.krishi.Adapters.InteractorAdapter;
import in.ascentrasolutions.krishi.Fetchers.InteractFetch;
import in.ascentrasolutions.krishi.Fetchers.VendorFetch;
import in.ascentrasolutions.krishi.Getters.Interactors;
import in.ascentrasolutions.krishi.R;


public class InteractingFragment extends Fragment {


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interacting, container, false);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);



        RecyclerView recyclerView = view.findViewById(R.id.interacting_recycler);
        ArrayList<Interactors> interactors = new ArrayList<>();
        InteractorAdapter interactingAdapter = new InteractorAdapter(interactors, requireContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(interactingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));



        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String id = sharedPreferences.getString(USER_ID, null);


        if(id != null) {

            VendorFetch vendorFetch = new VendorFetch(interactors, interactingAdapter);
            vendorFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/interactors/interactors?user_id=" + id);
        }



        return view;
    }
}