package in.ascentrasolutions.krishi.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.Fetchers.InteractFetch;
import in.ascentrasolutions.krishi.Fetchers.ProfileFetcher;
import in.ascentrasolutions.krishi.Fragments.InChatFragment;
import in.ascentrasolutions.krishi.Fragments.InteractingFragment;
import in.ascentrasolutions.krishi.Fragments.InteractorFragment;
import in.ascentrasolutions.krishi.R;


public class ProfileFragment extends Fragment {

    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);


        Bundle args = getArguments();

        if(args != null) {

            String profile_id = args.getString("profile_id");

            sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String id = sharedPreferences.getString(USER_ID, null);


            TextView profile_post, profile_post_count, profile_interactors, profile_interactors_count,
                    profile_interacting, profile_interacting_count, profile_user_text_head, profile_user_text;
            Button message, interactor, interacting;
            ImageView profile_user_image = view.findViewById(R.id.profile_user_image);

            profile_user_text = view.findViewById(R.id.profile_user_text);
            profile_user_text_head = view.findViewById(R.id.profile_user_text_head);
            //profile_post = view.findViewById(R.id.in_profile_posts);
            profile_post_count = view.findViewById(R.id.in_profile_post_count);
            profile_interactors = view.findViewById(R.id.in_profile_interactors);
            profile_interacting = view.findViewById(R.id.in_profile_interacting);
            profile_interacting_count = view.findViewById(R.id.in_profile_interacting_count);
            profile_interactors_count = view.findViewById(R.id.in_profile_interactors_count);
            message = view.findViewById(R.id.message_button);
            interactor = view.findViewById(R.id.interact_button);
            interacting = view.findViewById(R.id.interacting_button);


            ProfileFetcher profileFetcher = new ProfileFetcher(profile_user_text_head, profile_user_image, requireContext(), profile_post_count, profile_interacting_count, profile_interactors_count, profile_user_text);
            profileFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/signin/profile?user_id=" + profile_id);

            message.setOnClickListener(v -> changeFrag(new InChatFragment()));

            interactor.setOnClickListener(v-> {

                if (id != null) {

                    interactor.setVisibility(View.GONE);
                    interacting.setVisibility(View.VISIBLE);
                    InteractFetch interactFetch = new InteractFetch();
                    interactFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/interactors/interact?user_id=" + id + "&interacting_id=" + profile_id);
                } else {
                    Toast.makeText(requireContext(), R.string.logIn, Toast.LENGTH_SHORT).show();
                }
            });

            interacting.setOnClickListener(v-> {

                if (id != null) {

                    interactor.setVisibility(View.VISIBLE);
                    interacting.setVisibility(View.GONE);
                    InteractFetch interactFetch = new InteractFetch();
                    interactFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/interactors/remove_interact?user_id=" + id + "&interacting_id=" + profile_id);
                } else {
                    Toast.makeText(requireContext(), R.string.logIn, Toast.LENGTH_SHORT).show();
                }
            });

            InteractingFragment interactingFragment = new InteractingFragment();
            InteractorFragment interactorFragment = new InteractorFragment();

            profile_interactors.setOnClickListener(view1 -> changeFrag(interactorFragment));
            profile_interacting.setOnClickListener(view1 -> changeFrag(interactingFragment));
            profile_interacting_count.setOnClickListener(view1 -> changeFrag(interactingFragment));
            profile_interactors_count.setOnClickListener(view1 -> changeFrag(interactorFragment));

        } else {
            Toast.makeText(requireContext(), "Error occurred! Please try after some time", Toast.LENGTH_SHORT).show();
        }







        return view;
    }

    private void changeFrag(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, fragment).addToBackStack(null).commit();
    }
}