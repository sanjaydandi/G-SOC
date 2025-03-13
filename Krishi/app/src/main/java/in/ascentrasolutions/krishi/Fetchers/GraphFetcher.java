package in.ascentrasolutions.krishi.Fetchers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.common.graph.Graph;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ascentrasolutions.krishi.Getters.Company;

public class GraphFetcher {
    private static final String TAG = "companyFetcher";
    private final ArrayList<Graph> list;

    public GraphFetcher(ArrayList<Graph> list) {
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

                    String company_name = jsonObject.getString("company_name");
                    String company_image = jsonObject.getString("company_image");
                    String company_location = jsonObject.getString("company_location");
                    String company_number = jsonObject.getString("company_number");

                    Log.e(TAG, company_image);

                    //list.add(new Gra(company_image, company_location,  company_name, company_number));

                }


            } catch (Exception e){
                Log.e(TAG, e.toString());
            }


        }
    }

}
