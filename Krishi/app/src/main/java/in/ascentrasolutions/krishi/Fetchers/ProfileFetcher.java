package in.ascentrasolutions.krishi.Fetchers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ascentrasolutions.krishi.R;


public class ProfileFetcher {

    private final TextView in_profile_name, post_count, interactor_count, interacting_count, profile_user_text;
    private final ImageView in_profile_img;
    private final Context context;

    public ProfileFetcher(TextView in_profile_name, ImageView in_profile_img, Context context, TextView post_count, TextView interacting_count, TextView interactor_count, TextView profile_user_text) {
        this.in_profile_img = in_profile_img;
        this.in_profile_name = in_profile_name;
        this.context = context;
        this.interacting_count = interacting_count;
        this.interactor_count = interactor_count;
        this.post_count = post_count;
        this.profile_user_text = profile_user_text;
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

                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i< jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String profile_name = jsonObject.getString("name");
                    String profile_img = jsonObject.getString("profile");
                    String post_count = jsonObject.getString("post_count");
                    String interactor_count = jsonObject.getString("interactor_count");
                    String interacting_count = jsonObject.getString("interacting_count");


                    in_profile_name.setText(profile_name);
                    Glide.with(context)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .load(profile_img)
                            .error(R.drawable.app_logo)
                            .into(in_profile_img);

                    if (this.interacting_count != null && this.interactor_count != null && this.post_count != null && this.profile_user_text != null) {

                        this.post_count.setText(post_count);
                        this.interactor_count.setText(interactor_count);
                        this.interacting_count.setText(interacting_count);
                        profile_user_text.setText(profile_name);
                    }


                }

            } catch(Exception e) {
                Log.e("fetch", "fetch error");
            }

        } else {
            Log.e("fetch", "null fetch error");
        }

    }
}
