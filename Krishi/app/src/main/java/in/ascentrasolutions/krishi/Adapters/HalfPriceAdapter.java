package in.ascentrasolutions.krishi.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Fragments.CartFragment;
import in.ascentrasolutions.krishi.Getters.HalfPrice;
import in.ascentrasolutions.krishi.R;


public class HalfPriceAdapter extends RecyclerView.Adapter<HalfPriceAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<HalfPrice> halfPrices;

    public HalfPriceAdapter(Context context, ArrayList<HalfPrice> halfPrices) {
        this.context = context;
        this.halfPrices = halfPrices;
    }


    @NonNull
    @Override
    public HalfPriceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.half_price, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HalfPriceAdapter.ViewHolder holder, int position) {

        HalfPrice list = halfPrices.get(position);

        holder.name.setText(list.getName());
        holder.use_for.setText(list.getUse_for());
        holder.price.setText(holder.itemView.getContext().getString(R.string.price) + ": ₹" + list.getPrice());
        holder.quantity.setText(holder.itemView.getContext().getString(R.string.quantity) + ": " + list.getQuantity());

        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(list.getImage())
                .error(R.drawable.app_logo)
                .into(holder.image);

        holder.button.setOnClickListener(v -> {

        });

        holder.button.setOnClickListener(view -> {
            CartFragment cartFragment = new CartFragment();
            Bundle args = new Bundle();
            args.putString("crop_id", list.getCrop_id());
            cartFragment.setArguments(args);
            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, cartFragment).addToBackStack(null).commit();

        });

    }

    @Override
    public int getItemCount() {
        return halfPrices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, price, quantity, use_for;
        private final ImageView image;
        private final Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.yield_name);
            image = itemView.findViewById(R.id.yield_image);

            price= itemView.findViewById(R.id.yield_price);

            quantity = itemView.findViewById(R.id.yield_quantity);

            use_for = itemView.findViewById(R.id.yield_use_for);
            button = itemView.findViewById(R.id.yieldButton);


        }
    }
}
