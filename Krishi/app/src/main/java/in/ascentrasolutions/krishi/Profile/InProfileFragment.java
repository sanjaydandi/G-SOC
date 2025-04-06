package in.ascentrasolutions.krishi.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.Fetchers.ProfileFetcher;
import in.ascentrasolutions.krishi.Fragments.InteractingFragment;
import in.ascentrasolutions.krishi.Fragments.InteractorFragment;
import in.ascentrasolutions.krishi.R;
import in.ascentrasolutions.krishi.SignIn.ChooseFragment;


public class InProfileFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_in_profile, container, false);

        try {

            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.GONE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
        }


        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(USER_ID, null);

        if(id != null) {
            TextView profile_user_head,in_profile_post_count, in_profile_interactor_count, in_profile_interacting_count, profile_user_text, in_profile_interacting, in_profile_interactor;

            ImageView profile_user_image = view.findViewById(R.id.profile_user_image);
            in_profile_interacting_count = view.findViewById(R.id.in_profile_interacting_count);
            in_profile_post_count = view.findViewById(R.id.in_profile_post_count);
            in_profile_interactor_count = view.findViewById(R.id.in_profile_interactors_count);
            profile_user_head = view.findViewById(R.id.profile_user_text_head);
            profile_user_text = view.findViewById(R.id.profile_user_text);

            in_profile_interacting = view.findViewById(R.id.in_profile_interacting);
            in_profile_interactor= view.findViewById(R.id.in_profile_interactors);

            Button edit_profile = view.findViewById(R.id.message_button);

            edit_profile.setOnClickListener(v -> {
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                Bundle bb = new Bundle();
                bb.putString("profile_id", id);
                editProfileFragment.setArguments(bb);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, editProfileFragment).addToBackStack(null).commit();
            });


            ProfileFetcher profileFetcher = new ProfileFetcher(profile_user_head, profile_user_image, requireContext(), in_profile_post_count, in_profile_interacting_count, in_profile_interactor_count, profile_user_text);
            profileFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/signin/profile?user_id=" + id);

            in_profile_interacting_count.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new InteractingFragment()).addToBackStack(null).commit());
            in_profile_interactor_count.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new InteractorFragment()).addToBackStack(null).commit());
            in_profile_interacting.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new InteractingFragment()).addToBackStack(null).commit());
            in_profile_interactor.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new InteractorFragment()).addToBackStack(null).commit());

            ConstraintLayout logIn = view.findViewById(R.id.inProfileLogIn);

            logIn.setVisibility(View.GONE);
            SwipeRefreshLayout inProfileSwipe = view.findViewById(R.id.inProfileSwipe);
            inProfileSwipe.setVisibility(View.VISIBLE);

            inProfileSwipe.setOnRefreshListener(() -> {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new InProfileFragment()).commit();
                inProfileSwipe.setRefreshing(false);
            });


            profile_user_head.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new InProfileFragment()).addToBackStack(null).commit());


        } else {
            ConstraintLayout logIn = view.findViewById(R.id.inProfileLogIn);
            Button upload_signIn = view.findViewById(R.id.upload_signIn);

            upload_signIn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new ChooseFragment()).addToBackStack(null).commit());

            logIn.setVisibility(View.VISIBLE);
            SwipeRefreshLayout inProfileSwipe = view.findViewById(R.id.inProfileSwipe);
            inProfileSwipe.setVisibility(View.GONE);
        }


        return view;
    }
}