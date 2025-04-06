package in.ascentrasolutions.krishi.Uploads;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

import in.ascentrasolutions.krishi.Fetchers.UploadFercher;
import in.ascentrasolutions.krishi.Fragments.HalfPriceFragment;
import in.ascentrasolutions.krishi.R;
import in.ascentrasolutions.krishi.SignIn.ChooseFragment;

public class UploadFragment extends Fragment {
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        Button upload_post, upload_half_price, upload_sign_in;

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);

        try {
            toolbar.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "error occurred", Toast.LENGTH_SHORT).show();
        }

        upload_post = view.findViewById(R.id.upload_post);
        upload_half_price = view.findViewById(R.id.upload_half_price);
        upload_sign_in = view.findViewById(R.id.upload_signIn);

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String id = sharedPreferences.getString(USER_ID, null);

        if(id != null) {

            ConstraintLayout upload_sign = view.findViewById(R.id.upload_sign_in);
            upload_sign.setVisibility(View.GONE);
            ConstraintLayout upload_layout = view.findViewById(R.id.upload_layout);
            upload_layout.setVisibility(View.VISIBLE);


            upload_post.setOnClickListener(v ->
            {
                Intent i = new Intent(requireContext(), UploadActivity.class);
                startActivity(i);
            });
            upload_half_price.setOnClickListener(v ->
            {
                Intent i = new Intent(requireContext(), HalfPriceActivity.class);
                startActivity(i);
            });

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