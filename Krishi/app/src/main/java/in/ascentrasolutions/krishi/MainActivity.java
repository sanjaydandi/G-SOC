package in.ascentrasolutions.krishi;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import in.ascentrasolutions.krishi.Fragments.ChatFragment;
import in.ascentrasolutions.krishi.Fragments.CropFragment;
import in.ascentrasolutions.krishi.Fragments.HomeFragment;
import in.ascentrasolutions.krishi.Profile.InProfileFragment;
import in.ascentrasolutions.krishi.Fragments.MarketFragment;
import in.ascentrasolutions.krishi.Profile.NavProfileFragment;
import in.ascentrasolutions.krishi.SignIn.ChooseFragment;
import in.ascentrasolutions.krishi.Uploads.UploadFragment;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Menu currentMenu;
    private final HomeFragment homeFragment = new HomeFragment();
    private final MarketFragment marketFragment = new MarketFragment();
    private final InProfileFragment inProfileFragment = new InProfileFragment();
    private final CropFragment cropFragment = new CropFragment();
    private final NavProfileFragment navProfileFragment = new NavProfileFragment();
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    private static final String DARK_MODE = "dark_mode";
    String id;


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


        hideUI();
        loadLocale();


        getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, homeFragment).commit();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);



        id = sharedPreferences.getString(USER_ID, null);
        String darkMode = sharedPreferences.getString(DARK_MODE, null);

        if(darkMode != null && darkMode.equals("night")) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else if (darkMode != null && darkMode.equals("light")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }



     /*   LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000); // 10 seconds
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    Log.d("RealTimeLoc", "Lat: " + lat + ", Lon: " + lon);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
*/

        //Toast.makeText(this, darkMode, Toast.LENGTH_SHORT).show();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.home) {

                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, homeFragment).addToBackStack(null).commit();
                changeIcons(R.id.home);
            } else if(item.getItemId() == R.id.market) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, marketFragment).addToBackStack(null).commit();
                changeIcons(R.id.market);
            } else if(item.getItemId() == R.id.app_logo_tool) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, navProfileFragment).addToBackStack(null).commit();
                changeIcons(R.id.app_logo_tool);
            } else if(item.getItemId() == R.id.crops) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, cropFragment).addToBackStack(null).commit();
                changeIcons(R.id.crops);
            }
            return false;
        });




    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        currentMenu = menu;


        if(id != null) {
            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

            MenuItem menu1Item = currentMenu.findItem(R.id.signIn); // Use the reference to access the item
            //MenuItem logo = currentMenu.findItem(R.id.user_logo);

            if (menu1Item != null) {
                menu1Item.setVisible(false);
            }

        } else {
            //Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle the sign-in action
        int id = item.getItemId();


        if (id == R.id.chat) {
            ChatFragment chatFragment = new ChatFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, chatFragment).addToBackStack(null).commit();
        } else if(id == R.id.upload) {
            UploadFragment uf = new UploadFragment();

            /*Intent i = new Intent(MainActivity.this, UploadActivity.class);
            startActivity(i);*/

            getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, uf).addToBackStack(null).commit();
        } else if(id == R.id.signIn) {
            ChooseFragment chooseFragment = new ChooseFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, chooseFragment).addToBackStack(null).commit();
        }


        return super.onOptionsItemSelected(item);


    }

    public void resetIcons() {
        MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.home);

        if (menuItem != null) {
            menuItem.setIcon(R.drawable.house_door);

        }



    }

    public void changeIcons(int i) {
        resetIcons();

        MenuItem menuItem = bottomNavigationView.getMenu().findItem(i);

        if(menuItem != null) {
            if(i == R.id.home) {
                menuItem.setIcon(R.drawable.house_door_fill);
            }
        }
    }
    private void hideUI() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN // Hide status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide navigation bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; // Ensure the UI stays hidden

        decorView.setSystemUiVisibility(uiOptions);

    }

    private void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String language = preferences.getString("language", "en");
        setLocale(language);
    }

    // Apply selected locale dynamically
    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }



}