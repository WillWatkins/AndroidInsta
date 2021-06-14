package com.williamwatkins.androidinsta.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.williamwatkins.androidinsta.activities.OthersProfileActivity;
import com.williamwatkins.androidinsta.R;
import com.williamwatkins.androidinsta.models.UsersPost;

import java.util.ArrayList;
import java.util.Objects;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {

    //Firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference currentUserFollowing = firebaseDatabase.getReference().child("following");
    DatabaseReference currentUserFollowers = firebaseDatabase.getReference().child("followers");
    DatabaseReference usersPostsReference = firebaseDatabase.getReference().child("users_content");

    //Firebase Auth
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();

    String isFollowing = "false";

    Context adapterContext;
    private ArrayList<UsersPost> usersPosts;

    public SearchRecyclerViewAdapter(Context context, ArrayList<UsersPost> objects){
        this.adapterContext = context;
        usersPosts = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        View view = inflater.inflate(R.layout.search_recylerview_layout, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.username.setText(usersPosts.get(position).getUsername());
        holder.captionText.setText(usersPosts.get(position).getCaption());
        holder.likes.setText(usersPosts.get(position).getLikes() + " likes");
        holder.postImage.setImageBitmap(usersPosts.get(position).getPostImage());

        usersPostsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userIds: Objects.requireNonNull(snapshot).getChildren()){
                    for (DataSnapshot content: Objects.requireNonNull(userIds).getChildren()){

                        String retrievedUsername = content.child("username").getValue().toString();
                        String otherUsersUID = content.child("usersID").getValue().toString();

                        if (holder.username.getText().equals(retrievedUsername)) {

                            currentUserFollowing.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    try {
                                        isFollowing = snapshot.child(userID).child(otherUsersUID).getValue().toString();

                                        if (holder.username.getText().equals(retrievedUsername) && isFollowing.equals("true")){

                                            holder.follow.setText("Following");
                                        }

                                    } catch (Exception e) {

                                        System.out.println("Exception: " + e);
                                        holder.follow.setText("Follow");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return usersPosts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username, captionText, likes, follow;
        ImageView postImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.usernameTextView);
            captionText = itemView.findViewById(R.id.captionTextView);
            likes = itemView.findViewById(R.id.likesTextView);
            follow = itemView.findViewById(R.id.followTextView);
            postImage = itemView.findViewById(R.id.postImage);

            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    username.getContext().startActivity(new Intent(username.getContext(), OthersProfileActivity.class));
                }
            });

            //Need to change the below so it executes on the profile photo and not the post image- need to add profile photos.
//            postImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    username.getContext().startActivity(new Intent(username.getContext(), OthersProfileActivity.class));
//                }
//            });

            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    usersPostsReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot UserIDs: Objects.requireNonNull(snapshot).getChildren()){
                                for (DataSnapshot usersContent: Objects.requireNonNull(UserIDs).getChildren()) {

                                    String retrievedUsername = usersContent.child("username").getValue().toString();
                                    String otherUsersUID = usersContent.child("usersID").getValue().toString();

                                    //Need to check for the user they have chosen to follow otherwise it will add all users to their following list
                                    if (retrievedUsername.equals(username.getText().toString())){

                                        //If the user is not following the other users then this executes
                                        if (follow.getText().equals("Follow")){

                                            //Adds the other user to the current users following list
                                            currentUserFollowing.child(userID).child(otherUsersUID).setValue("true");

                                            //Add the current user to the other users followers list
                                            currentUserFollowers.child(otherUsersUID).child(userID).setValue("true");
                                        }

                                        //If the user is already following the other user then this executes
                                        else {
                                            currentUserFollowing.child(userID).child(otherUsersUID).removeValue();
                                            currentUserFollowers.child(otherUsersUID).child(userID).removeValue();
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

        }
    }
}
