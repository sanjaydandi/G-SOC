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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Fragments.DataFragment;
import in.ascentrasolutions.krishi.Getters.Soils;
import in.ascentrasolutions.krishi.R;

public class SoilAdapter extends RecyclerView.Adapter<SoilAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Soils> soils;

    public SoilAdapter(Context context, ArrayList<Soils> soils) {
        this.context = context;
        this.soils = soils;
    }

    @NonNull
    @Override
    public SoilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.soils, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoilAdapter.ViewHolder holder, int position) {
        Soils list = soils.get(position);

        holder.soil_name.setText(list.getSoil_name());

        Glide.with(context)
                .asBitmap()
                .load(list.getSoil_image())
                .error(R.drawable.app_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.soil_image);



        holder.soil_image.setOnClickListener(view -> {
            DataFragment dataFragment = new DataFragment();
            Bundle args = new Bundle();

            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();

            args.putString("cs_id", list.getSoil_id());
            dataFragment.setArguments(args);

            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, dataFragment).addToBackStack(null).commit();
        });

    }

    @Override
    public int getItemCount() {
        return soils.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView soil_name;
        private final ImageView soil_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            soil_image = itemView.findViewById(R.id.crop_image);
            soil_name = itemView.findViewById(R.id.crop_name);

        }
    }
}
