package in.ascentrasolutions.krishi.Fetchers;

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

import in.ascentrasolutions.krishi.Adapters.InteractorAdapter;
import in.ascentrasolutions.krishi.Getters.Interactors;

public class VendorFetch {

    private final ArrayList<Interactors> list;
    private final InteractorAdapter adapter;

    public VendorFetch(ArrayList<Interactors> list, InteractorAdapter adapter) {

        this.list = list;
        this.adapter = adapter;
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

                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("interactors");
                JSONArray profile = jsonObject.getJSONArray("profile");

                for (int i = 0; i <jsonArray.length(); i++) {
                    JSONObject interactors = jsonArray.getJSONObject(i);

                    String user_id = interactors.getString("interacting_id");


                    for (int j = 0; j <profile.length(); j++) {
                        JSONObject pro = profile.getJSONObject(j);

                        if(pro.getString("user_id").equals(user_id)) {
                            String name = pro.getString("name");
                            String pp = pro.getString("profile");
                            String user_type = pro.getString("user_type");

                            list.add(new Interactors(name, user_id, pp, user_type));
                        }

                    }

                }




                adapter.notifyDataSetChanged();

            } catch(Exception e) {
                Log.e("fetch", "fetch error");
            }

        } else {
            Log.e("fetch", "null fetch error");
        }

    }

}
