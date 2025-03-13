package in.ascentrasolutions.krishi.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Fragments.ChatFragment;
import in.ascentrasolutions.krishi.Getters.Company;
import in.ascentrasolutions.krishi.R;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {

    private final ArrayList<Company> list;
    private final Context context;

    public CompanyAdapter(Context context, ArrayList<Company> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.ViewHolder holder, int position) {

        Company list = this.list.get(position);

        holder.company_name.setText(list.getCompany_name());
        holder.company_address.setText(list.getCompany_number());
        Glide.with(context)
                .asBitmap()
                .error(R.drawable.app_logo)
                .load(list.getCompany_image())
                .into(holder.company_image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatFragment chatFragment = new ChatFragment();
                Bundle args = new Bundle();

                args.putString("company_id", list.getCompany_location());
                chatFragment.setArguments(args);
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, chatFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView company_name, company_address;
        private final ImageView company_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            company_name = itemView.findViewById(R.id.company_name);
            company_address= itemView.findViewById(R.id.company_address);
            company_image = itemView.findViewById(R.id.company_image);
        }
    }
}
