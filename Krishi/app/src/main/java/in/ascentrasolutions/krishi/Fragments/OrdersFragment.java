package in.ascentrasolutions.krishi.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.R;
import in.ascentrasolutions.krishi.SignIn.ChooseFragment;


public class OrdersFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        try {
            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }

        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(USER_ID, null);

        Bundle args = getArguments();



        if(id != null) {
            ConstraintLayout logIn = view.findViewById(R.id.inProfileLogIn);
            logIn.setVisibility(View.GONE);
        }

        if(args != null) {
            String suc = args.getString("suc");

            ConstraintLayout constraintLayout = view.findViewById(R.id.confirm_layout);
            constraintLayout.setVisibility(View.VISIBLE);

        }

        Button signIn = view.findViewById(R.id.upload_signIn);

        signIn.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new ChooseFragment()).addToBackStack(null).commit());


        return view;
    }
}