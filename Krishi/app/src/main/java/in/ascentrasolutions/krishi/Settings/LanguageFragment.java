package in.ascentrasolutions.krishi.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import in.ascentrasolutions.krishi.R;
import in.ascentrasolutions.krishi.MainActivity;

public class LanguageFragment extends Fragment {
    private boolean isHindi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        TextView telugu, hindi, english, kannada, tamil;

        isHindi = loadLanguage().equals("hi");

        telugu = view.findViewById(R.id.telugu);
        english = view.findViewById(R.id.english);
        tamil = view.findViewById(R.id.tamil);
        kannada = view.findViewById(R.id.kannada);
        hindi = view.findViewById(R.id.hindi);


        // Button Click Listener
        telugu.setOnClickListener(v -> {
            if (isHindi) {
                setLocale("en"); // Switch to English
            } else {
                setLocale("te"); // Switch to Hindi
            }
            restartApp(); // Restart app to apply changes
        });

        // Button Click Listener
        hindi.setOnClickListener(v -> {
            if (isHindi) {
                setLocale("en"); // Switch to English
            } else {
                setLocale("hi"); // Switch to Hindi
            }
            restartApp(); // Restart app to apply changes
        });

        // Button Click Listener
        english.setOnClickListener(v -> {
            if (isHindi) {
                setLocale("en"); // Switch to English
            } else {
                setLocale("en"); // Switch to Hindi
            }
            restartApp(); // Restart app to apply changes
        });

        // Button Click Listener
        kannada.setOnClickListener(v -> {
            if (isHindi) {
                setLocale("en"); // Switch to English
            } else {
                setLocale("ka"); // Switch to Hindi
            }
            restartApp(); // Restart app to apply changes
        });
        // Button Click Listener
        tamil.setOnClickListener(v -> {
            if (isHindi) {
                setLocale("en"); // Switch to English
            } else {
                setLocale("ta"); // Switch to Hindi
            }
            restartApp(); // Restart app to apply changes
        });


        return view;
    }


    // Function to change app language dynamically
    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Save selected language to SharedPreferences
        saveLanguage(langCode);
    }

    // Restart the application to apply the language change
    private void restartApp() {
        Intent intent = requireActivity().getIntent();
        requireActivity().finish();
        startActivity(intent);
    }

    // Save selected language to SharedPreferences
    private void saveLanguage(String langCode) {
        SharedPreferences preferences = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", langCode);
        editor.apply();
    }

    // Load saved language from SharedPreferences
    private String loadLanguage() {
        SharedPreferences preferences = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        return preferences.getString("language", "en"); // Default language is English
    }

}