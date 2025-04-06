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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.Fetchers.CartFetcher;
import in.ascentrasolutions.krishi.R;
import in.ascentrasolutions.krishi.SignIn.ChooseFragment;

public class CartFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

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
        if(args != null) {

            String crop_id = args.getString("crop_id");

            if (id != null) {
                ConstraintLayout logIn = view.findViewById(R.id.inProfileLogIn);
                logIn.setVisibility(View.GONE);

                ImageView imageView = view.findViewById(R.id.cart_image);


                EditText editText = view.findViewById(R.id.crop_value);


                TextView plus, minus;
                plus = view.findViewById(R.id.your_plus);
                minus = view.findViewById(R.id.your_minus);

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String val = editText.getText().toString().trim();

                        int i = Integer.parseInt(val);

                        i = i +1;

                        editText.setText(val);
                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String val = editText.getText().toString().trim();

                        int i = Integer.parseInt(val);

                        i = i - 1;

                        editText.setText(val);
                    }
                });

                Button h = view.findViewById(R.id.order);

                h.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OrdersFragment ordersFragment = new OrdersFragment();
                        Bundle bb = new Bundle();

                        bb.putString("suc", "suc");
                        ordersFragment.setArguments(bb);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, ordersFragment).commit();
                    }
                });



                CartFetcher cartFetcher = new CartFetcher(imageView, requireContext());
                cartFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/soil/cart?crop_id=" + crop_id);

            }

            Button signIn = view.findViewById(R.id.upload_signIn);

            signIn.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new ChooseFragment()).addToBackStack(null).commit());
        }
        return view;
    }
}