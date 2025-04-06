package in.ascentrasolutions.krishi.SignIn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import in.ascentrasolutions.krishi.Fetchers.CreateFetch;
import in.ascentrasolutions.krishi.Fetchers.SignInFetch;
import in.ascentrasolutions.krishi.Profile.InProfileFragment;
import in.ascentrasolutions.krishi.R;


public class SignInFragment extends Fragment {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    SharedPreferences sharedPreferences;
    private String user_type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);


        try {

            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.GONE);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.GONE);

        } catch (Exception e) {
            Toast.makeText(requireContext(), "error occurred", Toast.LENGTH_SHORT).show();
        }


        Bundle args = getArguments();

        if (args != null) {
            user_type = args.getString("user_type");

        }
        ImageView eye_open,  eye_close;
        EditText password = view.findViewById(R.id.password);
        Button logIn = view.findViewById(R.id.login_button);
        TextView forgot_password = view.findViewById(R.id.forgot_password);

        forgot_password.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new ForgotFragment()).addToBackStack(null).commit());

        logIn.setOnClickListener(v -> {

            EditText email = view.findViewById(R.id.email);

            String email_value = email.getText().toString().trim();
            String password_value = password.getText().toString().trim();

            TextView email_error, password_error, accounts_error;

            accounts_error = view.findViewById(R.id.accounts_error);
            email_error = view.findViewById(R.id.email_error);
            password_error = view.findViewById(R.id.password_error);

            if(!email_value.isEmpty()) {
                if (!password_value.isEmpty()) {

                    CreateFetch createFetch = new CreateFetch(requireContext(), accounts_error);
                    createFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/signin/login?email=" + email_value + "&password=" + password_value);

                } else {
                    email_error.setVisibility(View.GONE);
                    password_error.setVisibility(View.VISIBLE);
                    accounts_error.setVisibility(View.GONE);
                }
            } else {
                email_error.setVisibility(View.VISIBLE);
                password_error.setVisibility(View.GONE);
                accounts_error.setVisibility(View.GONE);
            }

        });

        eye_open = view.findViewById(R.id.eye_open);
        eye_close = view.findViewById(R.id.eye_close);


        eye_open.setOnClickListener(v -> {
            eye_close.setVisibility(View.VISIBLE);
            eye_open.setVisibility(View.GONE);
            password.setInputType(InputType.TYPE_CLASS_TEXT);
        });

        eye_close.setOnClickListener(view1 -> {
            eye_open.setVisibility(View.VISIBLE);

            eye_close.setVisibility(View.GONE);

            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        });


        TextView create_account = view.findViewById(R.id.createAccount);
        create_account.setOnClickListener(v-> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new CreateAccountFragment()).addToBackStack(null).commit());


        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        // Set the Sign-In button click listener programmatically
        Button signInButton = view.findViewById(R.id.signInGoogle);
        signInButton.setOnClickListener(v -> signIn());


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireActivity());
        if (account != null) {
            String googleId = account.getId();                // Google ID
            String googleName = account.getDisplayName();     // Name
            String googleEmail = account.getEmail();          // Email
            Uri googlePhoto = account.getPhotoUrl();// Profile Picture


            Log.d("GoogleSignIn", "Google ID: " + googleId);
            Log.d("GoogleSignIn", "Name: " + googleName);
            Log.d("GoogleSignIn", "Email: " + googleEmail);
            Log.d("GoogleSignIn", "Photo URL: " + googlePhoto);


        }

        return view;
    }





    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                int statusCode = e.getStatusCode();
                Log.w("GoogleSignIn", "Google sign-in failed: " + statusCode);
                Toast.makeText(requireActivity(), "Google Sign-In failed: " + statusCode, Toast.LENGTH_LONG).show();
                // This will help in identifying the error code in more detail
                Log.w("GoogleSignIn", "Error details: " + e.getLocalizedMessage());
            }
        }
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        SignInFetch signInFetch = new SignInFetch();
                        signInFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/signin/signin?name=" + account.getDisplayName()+ "&email=" + account.getEmail() +"&profile=" + account.getPhotoUrl() + "&user_id=" + account.getId() + "&user_type=" + user_type);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(USER_ID, account.getId());
                        editor.apply();

                        Toast.makeText(getActivity(), "Authentication Successful", Toast.LENGTH_SHORT).show();

                        InProfileFragment inProfileFragment = new InProfileFragment();
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, inProfileFragment).commit();
                    } else {
                        Toast.makeText(getActivity(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //Toast.makeText(getActivity(), "User already logged in", Toast.LENGTH_SHORT).show();
        }
    }




}