package in.ascentrasolutions.krishi.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.SoilAdapter;
import in.ascentrasolutions.krishi.Fetchers.HomeFetcherWeather;
import in.ascentrasolutions.krishi.Fetchers.SoilFetcher;
import in.ascentrasolutions.krishi.Getters.Soil;
import in.ascentrasolutions.krishi.R;


public class HomeFragment extends Fragment {

    private ArrayList arrayList, arrayList1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.green));

        RecyclerView recyclerView = view.findViewById(R.id.soil_recycler);
        ArrayList<Soil> list = new ArrayList<>();
        SoilAdapter adapter = new SoilAdapter(requireContext(), list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        SoilFetcher soilFetcher = new SoilFetcher(list, adapter, "soil_name", "soil_image");
        soilFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/soil/soil");



        RecyclerView recyclerView1 = view.findViewById(R.id.home_waste);
        ArrayList<Soil> cropList = new ArrayList<>();
        SoilAdapter cropAdapter = new SoilAdapter(requireContext(), cropList);

        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(cropAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        SoilFetcher cropFetcher = new SoilFetcher(list, adapter, "crop_name", "crop_image");
        cropFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/crops");



        EditText imageView = view.findViewById(R.id.editTextText);

        //imageView.setOnClickListener(v -> checkCameraPermission());

        //upload_image = view.findViewById(R.id.upload_image);
        imageView.setOnClickListener(v -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                Intent intent = new Intent(Intent.ACTION_PICK);


                intent.setType("image/*");
                startActivity(intent);

            }

        });


        TextView temp, hum, wind, rain;

        temp = view.findViewById(R.id.temp_home);
        hum = view.findViewById(R.id.humidity_home);
        wind = view.findViewById(R.id.windSpeed);
        rain = view.findViewById(R.id.rainfall);


        HomeFetcherWeather homeFetcherWeather = new HomeFetcherWeather(temp, hum, rain, wind);
        homeFetcherWeather.fetchData("https://api.weatherapi.com/v1/current.json?key=e2b3d6452ff94726815100645250103&q=ongole&aqi=yes");


        BarChart barChart = view.findViewById(R.id.barChart);


        getData();
        BarDataSet dataSet = new BarDataSet(arrayList, "Crops graph");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);


        dataSet.setColors(ContextCompat.getColor(requireContext(), R.color.lightBlue));
        dataSet.setValueTextColor(Color.BLACK);

        dataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);


        BarChart barChart1 = view.findViewById(R.id.barChartCultivation);

        getData1();

        BarDataSet dataSet1 = new BarDataSet(arrayList1, "Cultivation graph");
        BarData barData1 = new BarData(dataSet1);
        barChart1.setData(barData1);


        //dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet1.setColors(ContextCompat.getColor(requireContext(), R.color.lightBlue));
        dataSet1.setValueTextColor(Color.BLACK);

        dataSet1.setValueTextSize(16f);
        barChart1.getDescription().setEnabled(true);


        barChart.invalidate(); // Refresh the chart


        return view;
    }

    private void getData()
    {
        arrayList= new ArrayList();

        arrayList.add(new BarEntry(2f, 119));

        arrayList.add(new BarEntry(3f, 24));

        arrayList.add(new BarEntry(4f, 6));

        arrayList.add(new BarEntry(5f, 439));
        arrayList.add(new BarEntry(6f, 29));



    }

    private void getData1()
    {
        arrayList1 = new ArrayList();

        arrayList1.add(new BarEntry(2f, 9));

        arrayList1.add(new BarEntry(3f, 4));

        arrayList1.add(new BarEntry(4f, 5));

        arrayList1.add(new BarEntry(5f, 6));
        arrayList1.add(new BarEntry(6f, 11));


    }




}