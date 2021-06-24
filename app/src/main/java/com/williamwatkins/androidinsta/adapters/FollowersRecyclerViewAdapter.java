package com.williamwatkins.androidinsta.adapters;

import android.content.Context;
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
import com.williamwatkins.androidinsta.R;
import com.williamwatkins.androidinsta.models.Followers;

import java.util.ArrayList;
import java.util.Objects;

public class FollowersRecyclerViewAdapter extends RecyclerView.Adapter<FollowersRecyclerViewAdapter.MyViewHolder> {

    //Firebase Auth
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String currentUserID = firebaseAuth.getCurrentUser().getUid();

    //Firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference userDetailsReference = firebaseDatabase.getReference().child("registered_users");
    DatabaseReference currentUserFollowersReference = firebaseDatabase.getReference().child("followers").child(currentUserID);

    Context adapterContext;
    private ArrayList<Followers> followers;

    public FollowersRecyclerViewAdapter(Context context, ArrayList<Followers> objects){
        this.adapterContext = context;
        followers = objects;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        View view = inflater.inflate(R.layout.follow_recyclerview, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.username.setText(followers.get(position).getUsername());
        holder.displayName.setText(followers.get(position).getDisplayName());
        //holder.profilePhoto.setImageBitmap(followers.get(position).getProfilePhoto());
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username, displayName, followers;
        //ImageView profilePhoto;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.usernameFollowTextView);
            displayName = itemView.findViewById(R.id.displayNameFollowTextView);
            followers = itemView.findViewById(R.id.FollowingButton);
            //profilePhoto = itemView.findViewById(R.id.profilePhoto);

        }
    }

}
