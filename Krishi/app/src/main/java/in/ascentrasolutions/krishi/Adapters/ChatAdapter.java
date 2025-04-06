package in.ascentrasolutions.krishi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ascentrasolutions.krishi.Getters.Chats;
import in.ascentrasolutions.krishi.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final ArrayList<Chats> chats;
    private final Context context;
    public ChatAdapter(ArrayList<Chats> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }



    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        Chats list = chats.get(position);

        if(list.getLeft() != null) {

            holder.leftConstraint.setVisibility(View.VISIBLE);
            holder.left.setText(list.getLeft());
        }

        if(list.getRight() != null) {
            holder.rightConstraint.setVisibility(View.VISIBLE);

            holder.right.setText(list.getRight());
        }

    }



    @Override
    public int getItemCount() {
        return chats.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView left, right;
        private final ConstraintLayout leftConstraint, rightConstraint;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.chat_left);
            right = itemView.findViewById(R.id.chat_right);
            leftConstraint = itemView.findViewById(R.id.left_constraintLayout);
            rightConstraint = itemView.findViewById(R.id.right_constraint_layout);

        }
    }

}
