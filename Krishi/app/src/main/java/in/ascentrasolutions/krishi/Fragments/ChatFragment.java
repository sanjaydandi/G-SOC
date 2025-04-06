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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.ChatAdapter;
import in.ascentrasolutions.krishi.Adapters.InteractorAdapter;
import in.ascentrasolutions.krishi.Fetchers.ChatFetcher;
import in.ascentrasolutions.krishi.Fetchers.FirstChatFetcher;
import in.ascentrasolutions.krishi.Fetchers.VendorFetch;
import in.ascentrasolutions.krishi.Getters.Chats;
import in.ascentrasolutions.krishi.Getters.Interactors;
import in.ascentrasolutions.krishi.R;
import in.ascentrasolutions.krishi.SignIn.ChooseFragment;


public class ChatFragment extends Fragment {

    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        try {
            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String id = sharedPreferences.getString(USER_ID, null);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.chat_swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new ChatFragment()).commit();
            swipeRefreshLayout.setRefreshing(false);
        });


        if(id != null) {

            ConstraintLayout chat_signIn = view.findViewById(R.id.chat_signin);


            chat_signIn.setVisibility(View.GONE);
            ConstraintLayout chat_layout = view.findViewById(R.id.chat_layout);
            chat_layout.setVisibility(View.VISIBLE);


            RecyclerView recyclerView = view.findViewById(R.id.first_chat_recycler);
            ArrayList<Interactors> interactors = new ArrayList<>();
            InteractorAdapter interactorAdapter = new InteractorAdapter(interactors, requireContext());

            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(interactorAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));



            FirstChatFetcher interactFetch = new FirstChatFetcher(interactors, interactorAdapter);
            interactFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/chats/first_chat?user_id=" + id);


        } else {


            ConstraintLayout chat_signIn = view.findViewById(R.id.chat_signin);
            Button signInButton = view.findViewById(R.id.chat_signIn);

            chat_signIn.setVisibility(View.VISIBLE);

            signInButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new ChooseFragment()).addToBackStack(null).commit());

        }


        return view;
    }
}