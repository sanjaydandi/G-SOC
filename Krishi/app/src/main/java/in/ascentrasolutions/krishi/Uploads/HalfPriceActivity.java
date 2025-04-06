package in.ascentrasolutions.krishi.Uploads;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import in.ascentrasolutions.krishi.Helpers.FileUtils;
import in.ascentrasolutions.krishi.MainActivity;
import in.ascentrasolutions.krishi.R;

public class HalfPriceActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL;

    private Button btnSelect, btnUpload;
    private ImageView imageView;
    private Uri filePath;
    private Bitmap bitmap;
    private String selectedImagePath;


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    private static final String DARK_MODE = "dark_mode";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_half_price);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnSelect = findViewById(R.id.btnSelect);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imageView);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);



        id = sharedPreferences.getString(USER_ID, null);
        String darkMode = sharedPreferences.getString(DARK_MODE, null);


        if(id != null) {
            ConstraintLayout chat_signin = findViewById(R.id.chat_signin);
            chat_signin.setVisibility(View.GONE);
            ConstraintLayout upload_layout = findViewById(R.id.upload_layout);
            upload_layout.setVisibility(View.VISIBLE);
        } else {

            ConstraintLayout chat_signin = findViewById(R.id.chat_signin);
            chat_signin.setVisibility(View.VISIBLE);
            ConstraintLayout upload_layout = findViewById(R.id.upload_layout);
            upload_layout.setVisibility(View.GONE);
        }

        EditText price, name, quantity;
        price = findViewById(R.id.time_out_price);
        name = findViewById(R.id.time_out_name);
        quantity = findViewById(R.id.time_out_quantity);



        btnSelect.setOnClickListener(v -> openFileChooser());
        btnUpload.setOnClickListener(v -> {
            if (selectedImagePath != null) {


                if(id != null) {

                    String name_value = name.getText().toString().trim();


                    String price_value = price.getText().toString().trim();

                    String quantity_value = quantity.getText().toString().trim();

                    if(!name_value.isEmpty() && !price_value.isEmpty() && !quantity_value.isEmpty() ) {


                        UPLOAD_URL = "https://www.ascentrasolutions.in/apps/krishi/images/uploadhalfprice?name=" + name_value + "&price=" + price_value + "&quantity=" + quantity_value + "&user_id=" + id;
                        new UploadFileToServer().execute(selectedImagePath);


                    } else {
                        Toast.makeText(this, "All are required fields", Toast.LENGTH_SHORT).show();
                    }

                }


            } else {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
            }
        });


    }

        private void openFileChooser() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                    selectedImagePath = FileUtils.getPath(this, filePath);
                    btnUpload.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private class UploadFileToServer extends AsyncTask<String, Integer, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(HalfPriceActivity.this);
                progressDialog.setMessage("Uploading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String filePath = params[0];
                return uploadFile(filePath);
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                Toast.makeText(HalfPriceActivity.this, result, Toast.LENGTH_LONG).show();
                Intent i = new Intent(HalfPriceActivity.this, MainActivity.class);
                startActivity(i);
                Log.e("uploadFile", result);
            }

            private String uploadFile(String filePath) {
                String responseString = null;
                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;

                try {
                    File sourceFile = new File(filePath);

                    if (!sourceFile.isFile()) {
                        Log.e("UploadFile", "Source File not found: " + filePath);
                        return "Source File not found";
                    }

                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(UPLOAD_URL);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", filePath);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes("--" + boundary + "\r\n");
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + filePath + "\"" + "\r\n");
                    dos.writeBytes("\r\n");

                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dos.writeBytes("\r\n--" + boundary + "--\r\n");
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                    InputStream is = conn.getInputStream();
                    int ch;
                    StringBuilder sb = new StringBuilder();
                    while ((ch = is.read()) != -1) {
                        sb.append((char) ch);
                    }
                    responseString = sb.toString();
                    is.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    responseString = "Error while uploading: " + e.getMessage();
                }

                return responseString;
            }

    }
}