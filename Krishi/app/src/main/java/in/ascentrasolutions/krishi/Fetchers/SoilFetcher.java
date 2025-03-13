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

import in.ascentrasolutions.krishi.Adapters.SoilAdapter;
import in.ascentrasolutions.krishi.Getters.Soil;

public class SoilFetcher {

    private final ArrayList<Soil> list;
    private static final String TAG = "soilFetcher";
    private final String soil_name, soil_image;
    private final SoilAdapter adapter;
    public SoilFetcher(ArrayList<Soil> list, SoilAdapter adapter, String soil_name, String soil_image) {
        this.list = list;
        this.adapter = adapter;
        this.soil_image = soil_image;
        this.soil_name = soil_name;
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

                    String soil_name = jsonObject.getString(this.soil_name);
                    String soil_image = jsonObject.getString(this.soil_image);

                    Log.e(TAG, soil_image);

                    list.add(new Soil(soil_image, soil_name));

                }

                adapter.notifyDataSetChanged();


            } catch (Exception e){
                Log.e(TAG, e.toString());
            }


        }
    }

}
