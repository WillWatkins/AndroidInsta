package com.williamwatkins.androidinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button homeButton = findViewById(R.id.homeButton);
        Button searchButton = findViewById(R.id.seachButton);
        Button addPostButton = findViewById(R.id.newPostButton);
        Button marketplaceButton = findViewById(R.id.marketPlaceButton);
        Button profileButton = findViewById(R.id.profileButton);


//        //For retrieving the users
//        ListView userListView = findViewById(R.id.usersListView);
//        ArrayList<String> usernames = new ArrayList<>();
//
//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usernames);
//        userListView.setAdapter(arrayAdapter);
//
//        usersReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                usernames.clear();
//                for (DataSnapshot snapshot1: Objects.requireNonNull(snapshot).getChildren()){
//
//                    User users = snapshot1.getValue(User.class);
//                    String retrievedUsernames = users.getUsername();
//                    usernames.add(retrievedUsernames);
//                    Collections.sort(usernames);
//
//                }
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
    }




    public void homeButtonClicked(View view){
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

    public void searchButtonClicked(View view){
        startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
    }

    public void addPostButtonClicked(View view){
        startActivity(new Intent(ProfileActivity.this, AddPostActivity.class));
    }

    public void marketplaceButtonClicked(View view){
        startActivity(new Intent(ProfileActivity.this, MarketplaceActivity.class));
    }

    public void profileButtonClicked(View view){
        startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
    }
}