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

import in.ascentrasolutions.krishi.Adapters.SignYieldAdapter;
import in.ascentrasolutions.krishi.Getters.Company;
import in.ascentrasolutions.krishi.Getters.Yield;

public class YieldFetcher {
    private static final String TAG = "companyFetcher";
    private final ArrayList<Yield> list;
    private final SignYieldAdapter adapter;

    public YieldFetcher(ArrayList<Yield> list, SignYieldAdapter adapter) {

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
            Log.e("fetch", "error");
        }

        return result.toString();

    }

    private void onDataFetched(String result) {
        if(result != null) {

            try {

                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i <jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String company_name = jsonObject.getString("name");
                    String company_image = jsonObject.getString("image");
                    String company_location = jsonObject.getString("quantity");
                    String company_number = jsonObject.getString("price");
                    String use_for = jsonObject.getString("use_for");

                    Log.e(TAG, company_image);

                    list.add(new Yield(company_name, company_location, company_number, company_image, use_for));

                }

                adapter.notifyDataSetChanged();


            } catch (Exception e){
                Log.e(TAG, e.toString());
            }


        }
    }

}
