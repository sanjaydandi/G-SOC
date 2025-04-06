package in.ascentrasolutions.krishi.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.Fetchers.ProfileFetcher;
import in.ascentrasolutions.krishi.Fragments.CartFragment;
import in.ascentrasolutions.krishi.Fragments.HalfPriceFragment;
import in.ascentrasolutions.krishi.Fragments.OrdersFragment;
import in.ascentrasolutions.krishi.Fragments.VendorsFragment;
import in.ascentrasolutions.krishi.Fragments.WeatherFragment;
import in.ascentrasolutions.krishi.R;
import in.ascentrasolutions.krishi.Settings.SettingsFragment;


public class NavProfileFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_profile, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);

        try {
            toolbar.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "error occurred", Toast.LENGTH_SHORT).show();
        }


        TextView weather, settings, halfPrice, view_profile, vendors, nav_pro, orders, cart;
        ImageView profile_user_image = view.findViewById(R.id.profile_user_image);

        ConstraintLayout profile_nav_bar = view.findViewById(R.id.profile_nav_bar);


        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(USER_ID, null);

        if (id != null) {
            profile_nav_bar.setVisibility(View.VISIBLE);
            nav_pro = view.findViewById(R.id.nav_pro);
            ProfileFetcher profileFetcher = new ProfileFetcher(nav_pro, profile_user_image, requireContext(), null, null, null, null);
            profileFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/signin/profile?user_id=" + id);

        } else {
            profile_nav_bar.setVisibility(View.GONE);
        }



        weather = view.findViewById(R.id.weather_nav);
        settings = view.findViewById(R.id.setting_nav);
        view_profile = view.findViewById(R.id.view_profile_nav);
        vendors = view.findViewById(R.id.vendors_nav);
        halfPrice = view.findViewById(R.id.half_price_nav);
        orders = view.findViewById(R.id.orders_nav);
        cart = view.findViewById(R.id.carts_nav);


        weather.setOnClickListener(v -> changFrag(new WeatherFragment()));
        settings.setOnClickListener(v -> changFrag(new SettingsFragment()));
        view_profile.setOnClickListener(v -> changFrag(new InProfileFragment()));
        vendors.setOnClickListener(v -> changFrag(new VendorsFragment()));
        halfPrice.setOnClickListener(v -> changFrag(new HalfPriceFragment()));
        orders.setOnClickListener(v -> changFrag(new OrdersFragment()));
        cart.setOnClickListener(v -> changFrag(new CartFragment()));




        return view;
    }

    private void changFrag(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, fragment).addToBackStack(null).commit();
    }
}