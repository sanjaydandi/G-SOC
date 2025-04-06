package in.ascentrasolutions.krishi.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.Fetchers.DataFetcher;
import in.ascentrasolutions.krishi.R;


public class DataFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_data);


        Bundle args = getArguments();

        if (args != null) {

            String cs_id = args.getString("cs_id");
            swipeRefreshLayout.setOnRefreshListener(() -> {
                Bundle args1 = new Bundle();
                DataFragment dataFragment = new DataFragment();

                args1.putString("cs_id", cs_id);
                dataFragment.setArguments(args1);
                swipeRefreshLayout.setRefreshing(false);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, dataFragment).commit();
            });

            assert cs_id != null;
            Log.e("soil", cs_id);

            TextView info = view.findViewById(R.id.cs_info);
            TextView grow = view.findViewById(R.id.cs_grow);
            TextView grow_duration = view.findViewById(R.id.cs_grow_duration);

            DataFetcher dataFetcher = new DataFetcher(info, grow, grow_duration);
            dataFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/soil/cs_data?cs_id=" + cs_id);

        } else {

            swipeRefreshLayout.setOnRefreshListener(() -> {

                Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                //requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new dataFragment).commit();
            });

        }


        return view;
    }
}