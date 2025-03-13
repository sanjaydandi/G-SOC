package in.ascentrasolutions.krishi.Fetchers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
import in.ascentrasolutions.krishi.R;

public class WeatherFetcher {

    private final static String TAG = "fetch";
    private final ArrayList<Weather> list;
    private final WeatherAdapter adapter;
    private final TextView location, temp_c;
    private final ImageView icon;
    private final Context context;


    public WeatherFetcher(ArrayList<Weather> list, WeatherAdapter adapter, TextView location, TextView temp_c, ImageView icon, Context context) {
        this.list = list;
        this.adapter = adapter;
        this.temp_c = temp_c;
        this.location = location;
        this.icon = icon;
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
        } catch(Exception e) {
            Log.e("fetch", "error");
        }

        return result.toString();

    }

    private void onDataFetched(String result) {

        if(result != null) {
            try {

                JSONObject jsonObject = new JSONObject(result);

                JSONObject location = jsonObject.getJSONObject("location");
                JSONObject current = jsonObject.getJSONObject("current");
                JSONObject condition1 = current.getJSONObject("condition");

                String location1 = location.getString("name");
                this.location.setText(location1);

                String tempo = current.getString("temp_c");
                temp_c.setText(tempo);

                String icon1  = condition1.getString("icon");

                Glide.with(context)
                        .asBitmap()
                        .error(R.drawable.app_logo)
                        .load("https:" + icon1)
                        .into(this.icon);

                Log.e(TAG, location1);




                JSONObject forecast = jsonObject.getJSONObject("forecast");


                JSONArray foreCastDay = forecast.getJSONArray("forecastday");

                for (int i = 0; i < foreCastDay.length(); i++) {
                    JSONObject date = foreCastDay.getJSONObject(i);


                    JSONArray hour = date.getJSONArray("hour");




                    JSONObject day = date.getJSONObject("day");
                    JSONObject condition_up = day.getJSONObject("condition");

                    String maxTemp_c = day.getString("maxtemp_c");
                    String minTemp_c = day.getString("mintemp_c");
                    String avgTemp_c = day.getString("avgtemp_c");
                    String maxWindKph = day.getString("maxwind_kph");
                    String itRain = day.getString("daily_will_it_rain");

                    String icon_up = condition_up.getString("icon");



                    ArrayList<WeatherHour> weatherHourList = new ArrayList<>();

                    for(int j = 0; j<hour.length(); j++) {

                        JSONObject hour_obj = hour.getJSONObject(j);

                        JSONObject condition = hour_obj.getJSONObject("condition");

                        String time = hour_obj.getString("time");
                        String temp_c = hour_obj.getString("temp_c");
                        String windKph = hour_obj.getString("wind_kph");
                        String humidity = hour_obj.getString("humidity");
                        String itRain1 = hour_obj.getString("will_it_rain");
                        String cloud = hour_obj.getString("cloud");
                        String wind_chill = hour_obj.getString("windchill_c");
                        String heat_c = hour_obj.getString("heatindex_c");
                        String dewPoint = hour_obj.getString("dewpoint_c");

                        String icon = condition.getString("icon");



                        weatherHourList.add(new WeatherHour(time, temp_c, windKph, humidity, itRain1, cloud, wind_chill, heat_c, dewPoint, icon));
                    }



                    Log.e("fetch", "success");

                    list.add(new Weather(icon_up, maxTemp_c, minTemp_c, avgTemp_c, maxWindKph, itRain, weatherHourList));
                }

                adapter.notifyDataSetChanged();




            } catch(Exception e) {
                Log.e(TAG, e.toString());
            }

        } else {
            Log.e(TAG, "null fetch error");
        }

    }




}
