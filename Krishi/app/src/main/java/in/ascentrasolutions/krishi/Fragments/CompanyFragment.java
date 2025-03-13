package in.ascentrasolutions.krishi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Adapters.CompanyAdapter;
import in.ascentrasolutions.krishi.Fetchers.CompanyFetcher;
import in.ascentrasolutions.krishi.Getters.Company;
import in.ascentrasolutions.krishi.R;


public class CompanyFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.company_recycler);
        ArrayList<Company> list = new ArrayList<>();
        CompanyAdapter adapter = new CompanyAdapter(requireContext(), list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        CompanyFetcher companyFetcher = new CompanyFetcher(list, adapter);
        companyFetcher.fetchData("https://www.ascentrasolutions.in/apps/krishi/company");


        return view;
    }
}