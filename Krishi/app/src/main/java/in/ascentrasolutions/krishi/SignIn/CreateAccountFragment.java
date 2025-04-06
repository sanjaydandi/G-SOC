package in.ascentrasolutions.krishi.SignIn;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.Fetchers.CreateFetch;
import in.ascentrasolutions.krishi.R;

public class CreateAccountFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        try {
            Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);

            toolbar.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.GONE);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }

        EditText fullName, email, password, repeatPassword;

        fullName = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.email_edit);
        password = view.findViewById(R.id.password_edit);
        repeatPassword = view.findViewById(R.id.password_edit_repeat);



        TextView alreadyAccount = view.findViewById(R.id.alreadyAccount);
        alreadyAccount.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new SignInFragment()).addToBackStack(null).commit());

        Button create_account = view.findViewById(R.id.createAccountButton);

        create_account.setOnClickListener(view2 -> {


            String fullNameValue = fullName.getText().toString().trim();
            String emailValue = email.getText().toString().trim();
            String passwordValue = password.getText().toString().trim();
            String passwordRepeatValue = repeatPassword.getText().toString().trim();

            TextView fullName1, email1, password1, repeatPassword1;


            fullName1 = view.findViewById(R.id.full_name_error);
            email1 = view.findViewById(R.id.email_error);
            password1 = view.findViewById(R.id.password_error);
            repeatPassword1 = view.findViewById(R.id.password_repeat_error);


            if(!fullNameValue.isEmpty()) {
                if(!emailValue.isEmpty()) {
                    if(!passwordValue.isEmpty()) {
                        if(!passwordRepeatValue.isEmpty()) {

                            if (passwordValue.equals(passwordRepeatValue)) {

                                fullName1.setVisibility(View.VISIBLE);
                                fullName1.setText(R.string.loading);

                                CreateFetch createFetch = new CreateFetch(requireContext(), fullName1);
                                createFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/signin/create_account?email=" + emailValue + "&password=" + passwordValue + "&fullname=" + fullNameValue);


                            } else {

                                fullName1.setVisibility(View.VISIBLE);
                                fullName1.setText(R.string.password_equal);
                                email1.setVisibility(View.GONE);
                                password1.setVisibility(View.GONE);
                                repeatPassword1.setVisibility(View.GONE);
                            }

                        } else {
                            fullName1.setVisibility(View.GONE);
                            email1.setVisibility(View.GONE);
                            password1.setVisibility(View.GONE);
                            repeatPassword1.setVisibility(View.VISIBLE);

                        }
                    } else {

                        fullName1.setVisibility(View.GONE);
                        email1.setVisibility(View.GONE);
                        password1.setVisibility(View.VISIBLE);
                        repeatPassword1.setVisibility(View.GONE);
                    }
                } else {
                    fullName1.setVisibility(View.GONE);
                    email1.setVisibility(View.VISIBLE);
                    password1.setVisibility(View.GONE);
                    repeatPassword1.setVisibility(View.GONE);

                }
            } else {
                fullName1.setVisibility(View.VISIBLE);
                email1.setVisibility(View.GONE);
                password1.setVisibility(View.GONE);
                repeatPassword1.setVisibility(View.GONE);
            }

        });


        return view;
    }
}