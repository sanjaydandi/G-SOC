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
import in.ascentrasolutions.krishi.Getters.Soil;
import in.ascentrasolutions.krishi.R;

public class SoilAdapter extends RecyclerView.Adapter<SoilAdapter.ViewHolder> {


    private final ArrayList<Soil> list;
    private final Context context;

    public SoilAdapter(Context context, ArrayList<Soil> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public SoilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.soil, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoilAdapter.ViewHolder holder, int position) {

        Soil list = this.list.get(position);

        holder.name.setText(list.getSoil_name());

        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.cloud_sun_fill)
                .load(list.getSoil_color())
                .into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            DataFragment dataFragment = new DataFragment();
            Bundle args = new Bundle();

            args.putString("", "");
            dataFragment.setArguments(args);

            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();

            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, dataFragment).addToBackStack(null).commit();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.soil_name);
            image = itemView.findViewById(R.id.soil_image);

        }
    }
}
