package in.ascentrasolutions.krishi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import in.ascentrasolutions.krishi.R;


public class DataFragment extends Fragment {

    private static final String TAG ="data";
    private TextView para;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);


        para = view.findViewById(R.id.para);


        fetchData("https://ascentrasolutions.in/apps/krishi/data?data=black");
        return view;
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

                    String para1 = jsonObject.getString("best_for");
                    //Log.e(TAG, company_image);
                    para.setText(para1);

                }


            } catch (Exception e){
                Log.e(TAG, e.toString());
            }


        }
    }



}