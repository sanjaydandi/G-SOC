package in.ascentrasolutions.krishi.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.Helpers.LinkOpen;
import in.ascentrasolutions.krishi.R;


public class SettingsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String DARK_MODE = "dark_mode";
    private static final String USER_ID = "user_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        TextView help, terms, language, feedback, logout_text;

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);

        try {
            toolbar.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.GONE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "error occurred", Toast.LENGTH_SHORT).show();
        }


        LinkOpen linkOpen = new LinkOpen(requireContext());

        help = view.findViewById(R.id.help_text);
        terms = view.findViewById(R.id.terms_text);
        language = view.findViewById(R.id.language_text);
        feedback = view.findViewById(R.id.feedback_text);
        logout_text = view.findViewById(R.id.logout_text);


        help.setOnClickListener(v -> linkOpen.OpenClick("https://www.ascentrasolutions.in/contact/"));
        terms.setOnClickListener(v -> linkOpen.OpenClick("https://www.ascentrasolutions.in/termsandpolicy/"));
        feedback.setOnClickListener(v -> linkOpen.OpenClick("https://www.ascentrasolutions.in/feedback/"));
        language.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new LanguageFragment()).addToBackStack(null).commit());


        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(USER_ID, null);

        SwitchCompat switchCompat = view.findViewById(R.id.switch_check);

        String darkMode = sharedPreferences.getString(DARK_MODE, null);

        if(darkMode != null && darkMode.equals("night")) {
            switchCompat.setChecked(true);
        }


        switchCompat.setOnClickListener(v -> {

            if(darkMode != null) {
                if (darkMode.equals("night")) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(DARK_MODE, "light");
                    edit.apply();

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    requireActivity().recreate();
                } else {

                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(DARK_MODE, "night");
                    edit.apply();

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    requireActivity().recreate();
                }
            } else {

                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(DARK_MODE, "night");
                edit.apply();

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                requireActivity().recreate();

            }

        });


        if(id != null) {

            logout_text.setVisibility(View.VISIBLE);
            logout_text.setOnClickListener(v -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(requireContext(), "Log out successfully", Toast.LENGTH_SHORT).show();
            });

        } else {
            logout_text.setVisibility(View.GONE);
        }

        return view;
    }
}