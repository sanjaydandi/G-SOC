package in.ascentrasolutions.krishi.SignIn;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.R;


public class OTPFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_o_t_p, container, false);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);

        try {
            toolbar.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.GONE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }




        return view;
    }
}