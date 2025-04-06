package in.ascentrasolutions.krishi.Uploads;

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


public class HalfPriceUploadFragment extends Fragment {
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_half_price_upload, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);

        try {
            toolbar.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.GONE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "error occurred", Toast.LENGTH_SHORT).show();
        }

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String id = sharedPreferences.getString(USER_ID, null);
        Button upload_sign_in = view.findViewById(R.id.upload_signIn);


        if(id != null) {

            ConstraintLayout upload_sign = view.findViewById(R.id.upload_sign_in);
            upload_sign.setVisibility(View.GONE);
            ConstraintLayout upload_layout = view.findViewById(R.id.upload_layout);
            upload_layout.setVisibility(View.VISIBLE);


        } else {
            ConstraintLayout upload_sign = view.findViewById(R.id.upload_sign_in);
            upload_sign.setVisibility(View.VISIBLE);
            ConstraintLayout upload_layout = view.findViewById(R.id.upload_layout);
            upload_layout.setVisibility(View.GONE);
            upload_sign_in.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new ChooseFragment()).addToBackStack(null).commit());

        }



        return view;
    }
}
