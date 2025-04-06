package in.ascentrasolutions.krishi.Fetchers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ascentrasolutions.krishi.Getters.HalfPrice;
import in.ascentrasolutions.krishi.R;

public class CartFetcher {


    private final ImageView imageView;
    private final Context context;

    public CartFetcher(ImageView imageView, Context context) {

        this.imageView = imageView;
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
            Log.e("fetch", e.toString());
        }

        return result.toString();
    }

    private void onDataFetched(String result) {

        if(result != null) {
            try {

                JSONObject jsonObject = new JSONObject(result);

                String image = jsonObject.getString("image");


                Glide.with(context)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(image)
                        .error(R.drawable.app_logo)
                        .into(imageView);

                    //list.add(new HalfPrice(name,  quantity,  price, image, use_for, crop_id));





            } catch(Exception e) {
                Log.e("fetch", "fetch error" + e);
            }

        } else {
            Log.e("fetch", "null fetch error");
        }

    }

}
