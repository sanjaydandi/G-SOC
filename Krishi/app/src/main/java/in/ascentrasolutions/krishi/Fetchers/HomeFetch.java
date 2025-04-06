package in.ascentrasolutions.krishi.Fetchers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ascentrasolutions.krishi.Adapters.HomeAdapter;
import in.ascentrasolutions.krishi.Getters.Posts;

public class HomeFetch {

    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    SharedPreferences sharedPreferences;
    private final HomeAdapter adapter;
    private final ArrayList<Posts> posts;
    private final Context context;

    public HomeFetch(HomeAdapter adapter, ArrayList<Posts> posts, Context context) {
        this.adapter = adapter;
        this.posts = posts;
        this.context = context;
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
        }  catch (Exception e) {
            Log.e("fetch", e.toString());
        }

        return result.toString();
    }

    private void onDataFetched(String result) {

        if(result != null) {
            try {

                JSONObject postsObject = new JSONObject(result);


                JSONArray jsonArray = postsObject.getJSONArray("posts");
                JSONArray profileArray = postsObject.getJSONArray("profile");

                for (int i = 0; i< jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String post_title = jsonObject.getString("post_title");
                    String post_image = jsonObject.getString("post_image");
                    String post_id = jsonObject.getString("post_id");
                    String profile_name = jsonObject.getString("profile_post_name");
                    String profile_likes = jsonObject.getString("likes");
                    String user_id = jsonObject.getString("uploaded_user_id");
                    String comments_count = jsonObject.getString("comments_count");
                    String post_bio = jsonObject.getString("bio");
                    String time = jsonObject.getString("time");
                    String profile = "", name ="";

                    for (int j = 0; j < profileArray.length(); j++) {
                        JSONObject profileObject = profileArray.getJSONObject(j);

                        if (profileObject.getString("user_id").equals(user_id)) {
                            profile = profileObject.getString("profile");
                            name = profileObject.getString("name");
                            break; // break once matched
                        }

                    }

                    sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    String id = sharedPreferences.getString(USER_ID, null);



                    String interacting_id = "";

                    boolean isInteracted = false;

                    boolean isLiked = false;
                    if(id != null) {

                        JSONArray interArray = postsObject.getJSONArray("interacting");
                        for (int k = 0; k < interArray.length(); k++) {
                            JSONObject inter = interArray.getJSONObject(k);


                            if (inter.getString("user_id").equals(id)) {
                                String interactingUserId = inter.getString("user_id");
                                String interactingId = inter.getString("interacting_id");

                                if (interactingUserId.equals(id) && interactingId.equals(user_id)) {
                                    isInteracted = true;
                                    break;
                                }
                            }
                        }

                        JSONArray likesArray = postsObject.getJSONArray("likes");
                        for (int k = 0; k < likesArray.length(); k++) {
                            JSONObject likes = likesArray.getJSONObject(k);


                            if (likes.getString("user_id").equals(id)) {
                                String interactingUserId = likes.getString("user_id");
                                String interactingId = likes.getString("post_id");

                                if (interactingUserId.equals(id) && interactingId.equals(post_id)) {
                                    isLiked = true;
                                    break;
                                }
                            }
                        }


                    }

                    posts.add(new Posts("https://www.ascentrasolutions.in/apps/krishi/images/" + post_image, profile_likes, name, profile_name, user_id, post_id, profile, post_bio, time, comments_count, isInteracted, isLiked));

                    Log.e("image", post_image);
                    Log.e("image", profile);
                }

                //home_connection_failure.setVisibility(View.GONE);

                adapter.notifyDataSetChanged();


            } catch(Exception e) {
                Log.e("fetch", "fetch error" + e);
            }

        } else {
            Log.e("fetch", "null fetch error");
        }

    }

}
