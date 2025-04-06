package in.ascentrasolutions.krishi.SignIn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.R;

public class ChooseFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, container, false);

        try {

            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.VISIBLE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Toast.makeText(requireContext(), "error occurred", Toast.LENGTH_SHORT).show();
        }


        CardView user, farmer;

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(USER_ID, null);

        if(id != null) {
            ConstraintLayout logined_layout = view.findViewById(R.id.logined_layout);
            logined_layout.setVisibility(View.VISIBLE);

            ConstraintLayout signin_layout = view.findViewById(R.id.signin_layout);
            signin_layout.setVisibility(View.GONE);

        } else {

            ConstraintLayout logined_layout = view.findViewById(R.id.logined_layout);
            logined_layout.setVisibility(View.GONE);

            ConstraintLayout signin_layout = view.findViewById(R.id.signin_layout);
            signin_layout.setVisibility(View.VISIBLE);

            user = view.findViewById(R.id.user_card_view);
            farmer = view.findViewById(R.id.farmer_card_view);

            SignInFragment signInFragment = new SignInFragment();
            Bundle args = new Bundle();

            user.setOnClickListener(view1 -> {

                args.putString("user_type", "user");
                signInFragment.setArguments(args);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, signInFragment).addToBackStack(null).commit();
            });

            farmer.setOnClickListener(view2 -> {
                args.putString("user_type", "farmer");

                signInFragment.setArguments(args);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, signInFragment).addToBackStack(null).commit();
            });

        }







        return view;
    }



}