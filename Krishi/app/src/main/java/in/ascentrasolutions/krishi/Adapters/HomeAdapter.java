package in.ascentrasolutions.krishi.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Fetchers.InteractFetch;
import in.ascentrasolutions.krishi.Fetchers.LikeFetch;
import in.ascentrasolutions.krishi.Fragments.InChatFragment;
import in.ascentrasolutions.krishi.Profile.ProfileFragment;
import in.ascentrasolutions.krishi.Getters.Posts;
import in.ascentrasolutions.krishi.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final ArrayList<Posts> list;
    private final Context context;
    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String USER_ID = "user_id";
    SharedPreferences sharedPreferences;

    public HomeAdapter(Context context, ArrayList<Posts> list) {
        this.list = list;
        this.context = context;

    }


    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {

        Posts posts = list.get(position);

        holder.post_title.setText(posts.getPost_title());

        holder.post_bio.setText(posts.getPost_bio());
        holder.like_icon_count.setText(posts.getPost_likes());

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(USER_ID, null);


        holder.post_image.post(() -> {
            Glide.with(context)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(posts.getPost_image())
                    .error(R.drawable.app_logo)
                    .into(holder.post_image);
        });

        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(posts.getProfile_image())
                .error(R.drawable.app_logo)
                .into(holder.post_profile_image);

        if (posts.isInteracted()) {
            holder.post_interact_button.setEnabled(false);
            holder.post_interacting_button.setEnabled(true);
            holder.post_interacting_button.setVisibility(View.VISIBLE);
            holder.post_interact_button.setVisibility(View.GONE);
        } else {
            holder.post_interact_button.setEnabled(true);
            holder.post_interacting_button.setEnabled(false);
        }


        if(posts.isLiked()) {
            holder.like_icon.setEnabled(false);
            holder.like_icon_fill.setEnabled(true);
            holder.like_icon_fill.setVisibility(View.VISIBLE);
            holder.like_icon.setVisibility(View.GONE);
        } else {
            holder.like_icon_fill.setEnabled(false);
            holder.like_icon.setEnabled(true);
            holder.like_icon_fill.setVisibility(View.GONE);
            holder.like_icon.setVisibility(View.VISIBLE);
        }

        holder.post_time.setText(posts.getPost_time());
        holder.comment_count.setText(posts.getComment_count());

        holder.post_profile_image.setOnClickListener(view -> {
            ProfileFragment inProfileFragment = new ProfileFragment();
            Bundle args = new Bundle();
            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
            args.putString("profile_id", posts.getProfile_id());

            inProfileFragment.setArguments(args);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, inProfileFragment).addToBackStack(null).commit();

        });

        holder.post_title.setOnClickListener(view -> {
            ProfileFragment inProfileFragment = new ProfileFragment();
            Bundle args = new Bundle();
            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
            args.putString("profile_id", posts.getProfile_id());

            inProfileFragment.setArguments(args);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, inProfileFragment).addToBackStack(null).commit();

        });

        holder.post_interact_button.setOnClickListener(view -> {

            if (id != null) {

                holder.post_interact_button.setVisibility(View.GONE);
                holder.post_interacting_button.setVisibility(View.VISIBLE);
                InteractFetch interactFetch = new InteractFetch();
                interactFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/interactors/interact?user_id=" + id + "&interacting_id=" + posts.getProfile_id());


            } else {
                Toast.makeText(context, "Log In to interact", Toast.LENGTH_SHORT).show();
            }
        });

        holder.post_interacting_button.setOnClickListener(view -> {

            if (id != null) {

                holder.post_interact_button.setVisibility(View.VISIBLE);
                holder.post_interacting_button.setVisibility(View.GONE);
                InteractFetch interactFetch = new InteractFetch();
                interactFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/interactors/remove_interact?user_id=" + id + "&interacting_id=" + posts.getProfile_id());
                Toast.makeText(context, posts.getProfile_id() , Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(context, "Log In to interact", Toast.LENGTH_SHORT).show();
            }
        });


        holder.like_icon.setOnClickListener( v-> {

            if(id != null) {

                holder.like_icon_fill.setVisibility(View.VISIBLE);
                holder.like_icon.setVisibility(View.GONE);

                LikeFetch likeFetch = new LikeFetch(holder.like_icon_count);

                likeFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/likes/like?user_id=" + id + "&post_id=" + posts.getPost_id());

                Toast.makeText(context, "liked", Toast.LENGTH_SHORT).show();
            }  else {
                Toast.makeText(context, "Log In to interact", Toast.LENGTH_SHORT).show();
            }
        });

        holder.like_icon_fill.setOnClickListener( v-> {

            if(id != null) {

                holder.like_icon_fill.setVisibility(View.GONE);
                holder.like_icon.setVisibility(View.VISIBLE);

                LikeFetch likeFetch = new LikeFetch(holder.like_icon_count);

                likeFetch.fetchData("https://www.ascentrasolutions.in/apps/krishi/likes/like?user_id=" + id + "&post_id=" + posts.getPost_id());

                Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Log In to interact", Toast.LENGTH_SHORT).show();
            }
        });


        holder.chat_icon.setOnClickListener(view -> {
            InChatFragment inChatFragment = new InChatFragment();
            Bundle args = new Bundle();

            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
            args.putString("sender_id", posts.getProfile_id());

            inChatFragment.setArguments(args);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, inChatFragment).addToBackStack(null).commit();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView post_title, like_icon_count, post_bio, post_time, comment_count;
        private final ImageView post_profile_image, post_image, like_icon, chat_icon, like_icon_fill;
        private final Button post_interact_button, post_interacting_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image_pp);
            post_title = itemView.findViewById(R.id.post_profile_name);
            post_profile_image = itemView.findViewById(R.id.post_profile_image);
            like_icon_count = itemView.findViewById(R.id.like_icon_count);
            post_interact_button = itemView.findViewById(R.id.post_interact_button);
            post_interacting_button = itemView.findViewById(R.id.post_interacting_button);
            like_icon = itemView.findViewById(R.id.like_icon);
            chat_icon = itemView.findViewById(R.id.chat_icon);
            post_bio = itemView.findViewById(R.id.post_bio);
            post_time = itemView.findViewById(R.id.post_time);
            comment_count = itemView.findViewById(R.id.chat_icon_count);
            like_icon_fill = itemView.findViewById(R.id.like_icon_fill);

        }
    }
}
