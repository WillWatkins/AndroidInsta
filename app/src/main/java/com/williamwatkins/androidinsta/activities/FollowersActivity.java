package com.williamwatkins.androidinsta.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.williamwatkins.androidinsta.R;
import com.williamwatkins.androidinsta.adapters.FeedRecyclerViewAdapter;
import com.williamwatkins.androidinsta.adapters.FollowersRecyclerViewAdapter;
import com.williamwatkins.androidinsta.models.Followers;
import com.williamwatkins.androidinsta.models.UsersPost;

import java.util.ArrayList;
import java.util.Objects;

public class FollowersActivity extends AppCompatActivity {

    //Firebase Auth
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String currentUserID = firebaseAuth.getCurrentUser().getUid();

    //Firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference userDetailsReference = firebaseDatabase.getReference().child("registered_users");
    DatabaseReference currentUserFollowersReference = firebaseDatabase.getReference().child("followers").child(currentUserID);

    FollowersRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        RecyclerView followersRecyclerView = findViewById(R.id.followersRecyclerView);
        ArrayList<Followers> followers = new ArrayList<>();
        recyclerViewAdapter = new FollowersRecyclerViewAdapter(this, followers);
        followersRecyclerView.setAdapter(recyclerViewAdapter);
        followersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        currentUserFollowersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot followersID : Objects.requireNonNull(snapshot).getChildren()) {
                    String retrievedUserID = followersID.getKey();

                    followers.clear();

                    userDetailsReference.child(retrievedUserID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.child("name").getValue().toString();
                            String username = snapshot.child("username").getValue().toString();

                            followers.add(new Followers(name, username, retrievedUserID));
                            recyclerViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}