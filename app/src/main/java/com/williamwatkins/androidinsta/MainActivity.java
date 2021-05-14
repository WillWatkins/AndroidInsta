package com.williamwatkins.androidinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference usersPostsReference;

    //Firebase Auth
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FeedRecyclerViewAdapter feedRecyclerViewAdapter;
    UsersPost retrievedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button homeButton = findViewById(R.id.homeButton);
        Button searchButton = findViewById(R.id.seachButton);
        Button addPostButton = findViewById(R.id.newPostButton);
        Button marketplaceButton = findViewById(R.id.marketPlaceButton);
        Button profileButton = findViewById(R.id.profileButton);

        //Set up for the recycler view with a custom adapter to show user posts
        RecyclerView feedRecyclerView = findViewById(R.id.feedRecyclerView);
        ArrayList<UsersPost> usersPosts= new ArrayList<>();
        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter(this, usersPosts);
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String userID = currentUser.getUid();
        usersPostsReference = firebaseDatabase.getReference().child("users_content");;

        //Retrieves the posts from the users on the firebase database and adds them to a recycler view
        usersPostsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersPosts.clear();
                //Below loops through to the users
                for (DataSnapshot UserIDs: Objects.requireNonNull(snapshot).getChildren()){
                    //Below loops through each users posts and creates a Userpost object with the content for the post
                    for (DataSnapshot usersContent: Objects.requireNonNull(UserIDs).getChildren()){
                            String username = usersContent.child("username").getValue().toString();
                            String caption = usersContent.child("caption").getValue().toString();
                            String likes = usersContent.child("likes").getValue().toString();
                            retrievedUser = new UsersPost(username, caption, likes);

                        //Adds the retrieved user to the array
                        usersPosts.add(retrievedUser);
                    }
                }
                //Once retrieved the data from the database, updates the recycler view with the new array
                feedRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Button methods
    public void homeButtonClicked(View view){
       startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    public void searchButtonClicked(View view){
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }

    public void addPostButtonClicked(View view){
        startActivity(new Intent(MainActivity.this, AddPostActivity.class));
    }

    public void marketplaceButtonClicked(View view){
        startActivity(new Intent(MainActivity.this, MarketplaceActivity.class));
    }

    public void profileButtonClicked(View view){
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    }
}