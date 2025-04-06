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
import in.ascentrasolutions.krishi.Adapters.VendorsAdapter;
import in.ascentrasolutions.krishi.Getters.Interactors;

public class FarmerAndVendor {

    private final VendorsAdapter adapter;
    private final ArrayList<Interactors> list;

    public FarmerAndVendor (VendorsAdapter adapter, ArrayList<Interactors> list) {

        this.adapter = adapter;
        this.list = list;
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

                    String name = jsonObject.getString("name");
                    String image = jsonObject.getString("profile");
                    String user_id = jsonObject.getString("user_id");
                    String type = jsonObject.getString("user_type");

                    list.add(new Interactors(name, user_id, image, type));
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
