package in.ascentrasolutions.krishi.Fetchers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ascentrasolutions.krishi.Adapters.WeatherAdapter;
import in.ascentrasolutions.krishi.Getters.Chats;
import in.ascentrasolutions.krishi.Getters.Weather;

public class CityFetcher {

    private final Context context;
    private final ArrayList<Weather> list;
    private final TextView location, temp_c;
    private final ImageView icon;
    private final WeatherAdapter adapter;
    public CityFetcher(Context context, ArrayList<Weather> list, TextView location, TextView tempC, ImageView icon, WeatherAdapter adapter) {

        this.context = context;
        this.list = list;
        this.location = location;
        temp_c = tempC;
        this.icon = icon;
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

                String city = "Unknown";

                if (jsonObject.has("address")) {
                    JSONObject address = jsonObject.getJSONObject("address");

                    if (address.has("city")) {
                        city = address.getString("city");
                    }

                    Log.d("City", city);
                } else {
                    Log.e("Error", "No address found in response");
                }



                WeatherFetcher fetch = new WeatherFetcher(list, adapter, location, temp_c, icon, context);
                fetch.fetchData("https://api.weatherapi.com/v1/forecast.json?key=e2b3d6452ff94726815100645250103&q=" + city +"&days=7&aqi=no&alerts=no");


            } catch(Exception e) {
                Log.e("fetch", "fetch error" + e);
            }

        } else {
            Log.e("fetch", "null fetch error");
        }

    }

}
