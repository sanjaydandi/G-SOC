package in.ascentrasolutions.krishi.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;

import in.ascentrasolutions.krishi.Adapters.ChatAdapter;
import in.ascentrasolutions.krishi.Fetchers.ChatFetcher;
import in.ascentrasolutions.krishi.Fetchers.InteractFetch;
import in.ascentrasolutions.krishi.Fetchers.ProfileFetcher;
import in.ascentrasolutions.krishi.Getters.Chats;
import in.ascentrasolutions.krishi.R;

public class InChatFragment extends Fragment {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    EditText editText;
    ImageView btnSpeak;
    Handler handler = new Handler();
    Runnable refreshRunnable;
    final int REFRESH_INTERVAL = 3000; // 3 seconds


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_chat, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

        Bundle args = getArguments();

        if (args != null) {


            sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String id = sharedPreferences.getString(USER_ID, null);

            String sender_id = args.getString("sender_id", null);



            if (id != null) {


                TextView chat_name = view.findViewById(R.id.chat_name);
                ImageView chat_image = view.findViewById(R.id.chat_image);
                ProfileFetcher profileFetcher = new ProfileFetcher(chat_name, chat_image, requireContext(), null, null, null, null);
                profileFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/signin/profile?user_id=" + sender_id);

                ConstraintLayout in_chat_fragment = view.findViewById(R.id.in_chat_layout);
                in_chat_fragment.setVisibility(View.VISIBLE);

                ConstraintLayout chatFragment = view.findViewById(R.id.chatSignIn);
                chatFragment.setVisibility(View.GONE);

                RecyclerView recyclerView = view.findViewById(R.id.chat_recycler);
                ArrayList<Chats> chat = new ArrayList<>();
                ChatAdapter chatAdapter = new ChatAdapter(chat, requireContext());

                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(chatAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));





                refreshRunnable = new Runnable() {
                    @Override
                    public void run() {
                        chat.clear();
                        ChatFetcher chatFetcher = new ChatFetcher(chatAdapter, chat, sender_id, id, recyclerView);

                        chatFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/chats/chat?receiver_id=" + id + "&sender_id=" + sender_id);

                        // Re-run after interval
                        handler.postDelayed(this, REFRESH_INTERVAL);
                    }
                };

                handler.post(refreshRunnable);





                Toast.makeText(requireContext(), sender_id, Toast.LENGTH_SHORT).show();
                editText = view.findViewById(R.id.chatText);
                btnSpeak = view.findViewById(R.id.chat_mic);
                chat_image = view.findViewById(R.id.chat_image);
                chat_name = view.findViewById(R.id.chat_name);


                // Button click listener to start speech recognition
                btnSpeak.setOnClickListener(v -> startVoiceInput());


                ImageView chat_send = view.findViewById(R.id.chat_send);

                chat_send.setOnClickListener( v-> {

                    Toast.makeText(requireContext(), "sending",Toast.LENGTH_SHORT).show();


                    String val = editText.getText().toString().trim();

                    InteractFetch interactFetch = new InteractFetch();
                    interactFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/chats/insert_chat?sender_id=" + sender_id + "&receiver_id=" + id + "&message=" + val);


                });



            } else {
                ConstraintLayout in_chat_fragment = view.findViewById(R.id.chatSignIn);
                in_chat_fragment.setVisibility(View.VISIBLE);

                ConstraintLayout chatFragment = view.findViewById(R.id.in_chat_layout);
                chatFragment.setVisibility(View.GONE);


            }

        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(refreshRunnable);
    }

    // Start voice input
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Your device does not support speech input", Toast.LENGTH_SHORT).show();
        }
    }

    // Get result from voice input and set to EditText
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == requireActivity().RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && !result.isEmpty()) {
                    editText.setText(result.get(0)); // Set recognized text to EditText
                }
            }
        }
    }


}