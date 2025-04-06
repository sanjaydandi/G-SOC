package in.ascentrasolutions.krishi.Fetchers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ascentrasolutions.krishi.Fragments.HomeFragment;
import in.ascentrasolutions.krishi.R;

public class CreateFetch {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_details";
    private static final String USER_ID = "user_id";

    private final Context context;
    private final TextView accounts_errors;

    public CreateFetch(Context context, TextView accounts_errors) {
        this.context = context;
        this.accounts_errors = accounts_errors;
    }


    public void fetchData(String url_link) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            String result = fetchDataFromUrl(url_link);

            new Handler(Looper.getMainLooper()).post(() -> onDataFetched(result));
        });

        executorService.shutdown();
    }

    private String fetchDataFromUrl(String url_link) {

        StringBuilder result =  new StringBuilder();

        try {
            URL url = new URL(url_link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;

                while((line = br.readLine()) != null) {
                    result.append(line);
                }
            } finally {
                httpURLConnection.disconnect();
            }
        } catch(Exception e) {
            Log.e("fetch", e.toString());
        }

        return result.toString();
    }

    private void onDataFetched(String result) {

        if(result != null) {
            try {

                JSONObject jsonObject = new JSONObject();

                String user_id = jsonObject.getString("message");

                if(user_id.equals("empty")) {
                    accounts_errors.setVisibility(View.VISIBLE);
                    accounts_errors.setText(R.string.technicalError);
                } else if(user_id.equals("noaccounts")) {
                    accounts_errors.setVisibility(View.VISIBLE);
                    accounts_errors.setText(R.string.noAccounts);
                } else {
                    sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USER_ID, user_id);
                    editor.apply();

                    HomeFragment homeFragment = new HomeFragment();
                    AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, homeFragment).commit();

                }


            } catch(Exception e) {
                Log.e("fetch", "fetch error");
            }

        } else {
            Log.e("fetch", "null fetch error");
        }

    }

}
