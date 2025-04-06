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

import in.ascentrasolutions.krishi.Fragments.InChatFragment;
import in.ascentrasolutions.krishi.Getters.Interactors;
import in.ascentrasolutions.krishi.R;

public class InteractorAdapter extends RecyclerView.Adapter<InteractorAdapter.ViewHolder> {

    private final ArrayList<Interactors> interactors;
    private final Context context;
    public InteractorAdapter(ArrayList<Interactors> interactors, Context context) {
        this.interactors = interactors;
        this.context = context;
    }


    @NonNull
    @Override
    public InteractorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interactors, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InteractorAdapter.ViewHolder holder, int position) {
        Interactors list = interactors.get(position);

        holder.user_type.setText(list.getUser_type());
        holder.interactor_name.setText(list.getInteractor_name());

        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(list.getInteractor_profile())
                .error(R.drawable.app_logo)
                .into(holder.image);
        holder.message.setOnClickListener(view -> {
            InChatFragment inChatFragment = new InChatFragment();
            Bundle args = new Bundle();

            args.putString("sender_id", list.getInteractor_id());
            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
            inChatFragment.setArguments(args);

            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, inChatFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return interactors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView interactor_name, user_type;
        private final ImageView image;
        private final Button message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            interactor_name = itemView.findViewById(R.id.interactor_text);
            user_type = itemView.findViewById(R.id.user_type_text);
            image = itemView.findViewById(R.id.interactors_img);
            message = itemView.findViewById(R.id.interact_msg);
        }
    }
}
