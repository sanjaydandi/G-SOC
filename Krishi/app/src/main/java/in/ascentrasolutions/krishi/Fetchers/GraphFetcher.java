package in.ascentrasolutions.krishi.Fetchers;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ascentrasolutions.krishi.Getters.Graph;

public class GraphFetcher {

    private final LineChart lineChart;

    public GraphFetcher( LineChart lineChart) {
        this.lineChart = lineChart;
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

                ArrayList<Entry> entries = new ArrayList<>();

                for (int i = 0; i< jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int x = jsonObject.getInt("value");
                    int y = jsonObject.getInt("valuey");

                    // Sample data points (x, y)
                    entries.add(new Entry(x, y));
                }



                // Create LineDataSet with entries
                LineDataSet lineDataSet = new LineDataSet(entries, "");
                lineDataSet.setColor(Color.BLUE);
                lineDataSet.setValueTextColor(Color.BLACK);
                lineDataSet.setLineWidth(2f);
                lineDataSet.setCircleColor(Color.RED);
                lineDataSet.setCircleRadius(5f);
                lineDataSet.setDrawCircleHole(false);
                lineDataSet.setDrawValues(true);

                // Create LineData and set to LineChart
                LineData lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);

                // Customize X-Axis
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setDrawGridLines(false);

                // Customize Y-Axis
                YAxis leftAxis = lineChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);

                lineChart.getAxisRight().setEnabled(false); // Hide right Y-Axis

                // Refresh chart
                lineChart.invalidate();


            } catch(Exception e) {
                Log.e("fetch", "fetch error");
            }

        } else {
            Log.e("fetch", "null fetch error");
        }

    }


}
