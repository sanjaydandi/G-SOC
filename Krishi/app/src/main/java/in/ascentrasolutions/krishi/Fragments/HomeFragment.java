package in.ascentrasolutions.krishi.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.HomeAdapter;
import in.ascentrasolutions.krishi.Fetchers.HomeFetch;
import in.ascentrasolutions.krishi.Getters.Posts;
import in.ascentrasolutions.krishi.R;


public class HomeFragment extends Fragment {
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    SharedPreferences sharedPreferences;


    SwipeRefreshLayout swipeRefreshLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    ConstraintLayout constraintLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }


        swipeRefreshLayout = view.findViewById(R.id.home_swipe);

        RecyclerView recyclerView = view.findViewById(R.id.home_recycler);
        ArrayList<Posts> posts = new ArrayList<>();
        HomeAdapter adapter = new HomeAdapter(requireContext(), posts);

        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        recyclerView.setVisibility(View.INVISIBLE);

        shimmerFrameLayout.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            recyclerView.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.INVISIBLE);
        }, 3000);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        ConstraintLayout home_connection_failure = view.findViewById(R.id.home_connection_failure);

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String id = sharedPreferences.getString(USER_ID, null);

        if(id != null) {

            HomeFetch homeFetch = new HomeFetch(adapter, posts, requireContext());
            homeFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/posts/posts?user_id=" + id);
        } else {

            HomeFetch homeFetch = new HomeFetch(adapter, posts, requireContext());
            homeFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/posts/posts");

        }



        swipeRefreshLayout.setOnRefreshListener(this::refresh);

        return view;


    }

    private void refresh() {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new HomeFragment()).commit();
        swipeRefreshLayout.setRefreshing(false);
    }
}