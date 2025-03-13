package in.ascentrasolutions.krishi.Fetchers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import in.ascentrasolutions.krishi.Getters.Weather;
import in.ascentrasolutions.krishi.Getters.WeatherHour;

public class HomeFetcherWeather {


    private final static String TAG = "fetch";

    private final TextView temp, hum, rain, wind;


    public HomeFetcherWeather(TextView temp1, TextView hum1, TextView rain, TextView wind) {

        this.temp = temp1;
        this.hum = hum1;
        this.rain = rain;
        this.wind = wind;
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

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(url_link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;

                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            } finally {
                httpURLConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("fetch", "error");
        }

        return result.toString();

    }

    private void onDataFetched(String result) {

        if (result != null) {
            try {

                JSONObject jsonObject = new JSONObject(result);

                JSONObject current = jsonObject.getJSONObject("current");


                String humidity = current.getString("humidity");

                String temperature = current.getString("temp_c");

                String windSpeed = current.getString("wind_kph");
                String rainFall = current.getString("cloud");


                temp.setText(temperature);
                hum.setText(humidity);
                wind.setText(windSpeed);
                rain.setText(rainFall);

                Log.e(TAG, temperature);




            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

        } else {
            Log.e(TAG, "null fetch error");
        }

    }

}



