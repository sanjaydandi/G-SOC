package in.ascentrasolutions.krishi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Getters.Yield;
import in.ascentrasolutions.krishi.R;

public class SignYieldAdapter extends RecyclerView.Adapter<SignYieldAdapter.ViewHolder> {

    private final ArrayList<Yield> list;
    private final Context context;


    public SignYieldAdapter(ArrayList<Yield> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SignYieldAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yield, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SignYieldAdapter.ViewHolder holder, int position) {

        Yield lis = list.get(position);


        holder.name.setText(lis.getName());
        holder.quantity.setText("quantity: " + lis.getQuantity() + "kgs");
        holder.price.setText("price: " + lis.getPrice() + "₹");
        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.app_logo)
                .load(lis.getImage())
                .into(holder.image);

        holder.use_for.setText("use for: " + lis.getUse_for());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, quantity, price, use_for;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.yield_name);
            image = itemView.findViewById(R.id.yield_image);
            quantity = itemView.findViewById(R.id.yield_quantity);
            price = itemView.findViewById(R.id.yield_price);
            use_for = itemView.findViewById(R.id.yield_use_for);

        }
    }
}
