package in.ascentrasolutions.krishi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Getters.Interactors;
import in.ascentrasolutions.krishi.R;

public class InteractingAdapter extends RecyclerView.Adapter<InteractingAdapter.ViewHolder> {

    private final ArrayList<Interactors> interactors;
    private final Context context;

    public InteractingAdapter(ArrayList<Interactors> interactors, Context context) {
        this.interactors = interactors;
        this.context = context;
    }

    @NonNull
    @Override
    public InteractingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interactors, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InteractingAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return interactors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
