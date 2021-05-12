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
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {



    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    FeedRecyclerViewAdapter feedRecyclerViewAdapter;
    DatabaseReference usersPostsReference = firebaseDatabase.getReference().child("registeredUsers").child("WillWatkins").child("Posts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button homeButton = findViewById(R.id.homeButton);
        Button searchButton = findViewById(R.id.seachButton);
        Button addPostButton = findViewById(R.id.newPostButton);
        Button marketplaceButton = findViewById(R.id.marketPlaceButton);
        Button profileButton = findViewById(R.id.profileButton);

        RecyclerView feedRecyclerView = findViewById(R.id.feedRecyclerView);
        ArrayList<UsersPost> usersPosts= new ArrayList<>();

        feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter(this, usersPosts);
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);

        usersPostsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1: Objects.requireNonNull(snapshot).getChildren()){

                    UsersPost getUsers = snapshot1.getValue(UsersPost.class);
                    UsersPost retrievedUser = new UsersPost(getUsers.getUsername(), getUsers.getCaption(), getUsers.getNumberOfLikes());

                    System.out.println("number of likes " + getUsers.getNumberOfLikes());
                    usersPosts.add(retrievedUser);
                    System.out.println("UserDetails:" + retrievedUser.getUsername() + " " + retrievedUser.getCaption() + " " + retrievedUser.getNumberOfLikes());

                }

                feedRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Menu inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /* Menu Items

    Current Items:
        Settings Activity
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(SearchActivity.this, Settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void homeButtonClicked(View view){
        startActivity(new Intent(SearchActivity.this, MainActivity.class));
    }

    public void searchButtonClicked(View view){
        startActivity(new Intent(SearchActivity.this, SearchActivity.class));
    }

    public void addPostButtonClicked(View view){
        startActivity(new Intent(SearchActivity.this, AddPostActivity.class));
    }

    public void marketplaceButtonClicked(View view){
        startActivity(new Intent(SearchActivity.this, MarketplaceActivity.class));
    }

    public void profileButtonClicked(View view){
        startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
    }
}