package in.ascentrasolutions.krishi.Fetchers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ascentrasolutions.krishi.Adapters.ChatAdapter;
import in.ascentrasolutions.krishi.Getters.Chats;

public class ChatFetcher {

    private final ChatAdapter chatAdapter;
    private final ArrayList<Chats> chats;
    private String sender_id, user_id;
    private final RecyclerView recyclerView;
    public ChatFetcher(ChatAdapter chatAdapter, ArrayList<Chats> chats, String sender_id, String user_id, RecyclerView recyclerView) {
        this.sender_id = sender_id;
        this.user_id = user_id;
        this.chatAdapter = chatAdapter;
        this.chats = chats;
        this.recyclerView = recyclerView;
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
                chats.clear();

                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i< jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String id = jsonObject.getString("id");
                    String sender = jsonObject.getString("sender");
                    String receiver = jsonObject.getString("receiver");
                    String message = jsonObject.getString("message");
                    String time = jsonObject.getString("time");

                    if (sender.equals(user_id)) {
                        chats.add(new Chats(message, null));
                    } else {
                        chats.add(new Chats(null, message));
                    }

                  /*  Log.d("debug", "Message: " + message + " | From: " + sender + " | To: " + receiver + " | You: " + user_id);



                    Log.e("chat", message);
*/
                }

                chatAdapter.notifyDataSetChanged();

                recyclerView.scrollToPosition(chats.size() - 1);


            } catch(Exception e) {
                Log.e("fetch", "fetch error" + e);
            }

        } else {
            Log.e("fetch", "null fetch error");
        }

    }

}
