package com.williamwatkins.androidinsta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.MyViewHolder> {

    Context adapterContext;
    private ArrayList<UsersPost> usersPosts;

    public FeedRecyclerViewAdapter(Context context, ArrayList<UsersPost> objects){

        this.adapterContext = context;
        usersPosts = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        View view = inflater.inflate(R.layout.insta_recyclerview_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.username.setText(usersPosts.get(position).getUsername());
        holder.captionText.setText(usersPosts.get(position).getCaption());
        holder.likes.setText(usersPosts.get(position).getLikes() + " likes");
        holder.postImage.setImageBitmap(usersPosts.get(position).getPostImage());
    }

    @Override
    public int getItemCount() {
        return usersPosts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username, captionText, likes;
        ImageView postImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTextView);
            captionText = itemView.findViewById(R.id.captionTextView);
            likes = itemView.findViewById(R.id.likesTextView);
            postImage = itemView.findViewById(R.id.postImage);
        }
    }
}
