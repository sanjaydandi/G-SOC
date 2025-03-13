package in.ascentrasolutions.krishi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ascentrasolutions.krishi.Fragments.CompanyFragment;
import in.ascentrasolutions.krishi.Fragments.HomeFragment;
import in.ascentrasolutions.krishi.Fragments.WeatherFragment;
import in.ascentrasolutions.krishi.Fragments.YieldFragment;
import in.ascentrasolutions.krishi.Helpers.Database;
import in.ascentrasolutions.krishi.SignIn.SignInFragment;

import android.Manifest;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView, upload_image;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 102;

    private static final int LOCATION_CODE = 103;

    private final HomeFragment homeFragment = new HomeFragment();
    private final CompanyFragment companyFragment = new CompanyFragment();
    private final WeatherFragment weatherFragment = new WeatherFragment();
    private BottomNavigationView bottomNavigationView;

    private TextView textView;
    private Menu currentMenu;
    private VoiceCommandReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");


        //Database database = new Database();
        //database.fetchData();
        requestLocationPermission();
        requestCameraPermission();




        hideSystemUI();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new HomeFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigation);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home) {

                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, homeFragment).addToBackStack(null).commit();
                changeIcons(R.id.home);

            } else if(item.getItemId() == R.id.companies) {

                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, companyFragment).addToBackStack(null).commit();
                changeIcons(R.id.companies);

            } else if(item.getItemId() == R.id.weather) {

                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, weatherFragment).addToBackStack(null).commit();
                changeIcons(R.id.weather);

            } else if(item.getItemId() == R.id.explore) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new YieldFragment()).addToBackStack(null).commit();
                changeIcons(R.id.explore);
            }  else {
                Toast.makeText(MainActivity.this, "error occurred", Toast.LENGTH_SHORT).show();
            }

            return false;
        });





        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                //Toast.makeText(this, "Not allowed", Toast.LENGTH_SHORT).show();
            }
        }



        //textView = findViewById(R.id.textView);

        // Start voice recognition service
      /*  Intent serviceIntent = new Intent(this, VoiceService.class);
        startService(serviceIntent);

        // Register broadcast receiver
        receiver = new VoiceCommandReceiver();
        IntentFilter filter = new IntentFilter("in.ascentrasolutions.krishi.SHOW_TEXT");
        //RECEIVER_EXPORTED.registerReceiver(receiver, filter);

        ContextCompat.registerReceiver(this, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);

*/






     /*   imageView = findViewById(R.id.camera_open);

        imageView.setOnClickListener(v -> checkCameraPermission());

        upload_image = findViewById(R.id.upload_image);
        upload_image.setOnClickListener(v -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                Intent intent = new Intent(Intent.ACTION_PICK);


                intent.setType("image/*");
                startActivity(intent);

            }

        });
*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    // BroadcastReceiver to receive voice command and update UI
    private class VoiceCommandReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            textView.setVisibility(View.VISIBLE);
        }
    }


    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN // Hide status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide navigation bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; // Ensure the UI stays hidden

        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);

        if (requestCode == 1 ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Not f allowed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void resetIcons() {
        MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.home);

        if (menuItem != null) {
            menuItem.setIcon(R.drawable.house_door);

        }

        MenuItem weatherItem = bottomNavigationView.getMenu().findItem(R.id.weather);

        if (weatherItem != null) {
            weatherItem.setIcon(R.drawable.cloud_sun);

        }

        MenuItem companiesItem = bottomNavigationView.getMenu().findItem(R.id.companies);

        if (companiesItem != null) {
            companiesItem.setIcon(R.drawable.buildings);

        }

        MenuItem exploreItem = bottomNavigationView.getMenu().findItem(R.id.explore);

        if (exploreItem != null) {
            exploreItem.setIcon(R.drawable.sign_yield);

        }
    }

    public void changeIcons(int i) {
        resetIcons();

        MenuItem menuItem = bottomNavigationView.getMenu().findItem(i);

        if(menuItem != null) {
            if(i == R.id.home) {
                menuItem.setIcon(R.drawable.house_door_fill);
            } else if(i == R.id.weather) {
                menuItem.setIcon(R.drawable.cloud_sun_fill);
            } else if(i == R.id.companies) {
                menuItem.setIcon(R.drawable.buildings_fill);
            } else if(i == R.id.explore) {
                menuItem.setIcon(R.drawable.sign_yield_fill);
            }
        }
    }



    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
        } else {
            //Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    public void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            //Toast.makeText(this, "camera error", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        currentMenu = menu;
        MenuItem signIn = menu.findItem(R.id.signIn);

        signIn.setOnMenuItemClickListener(menuItem -> {

            getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new SignInFragment()).commit();
            return false;
        });



        return true;
    }
}