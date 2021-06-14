package com.williamwatkins.androidinsta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.williamwatkins.androidinsta.R;

public class OthersProfileActivity extends AppCompatActivity {

    Boolean onOthersProfileActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile);

        Button homeButton = findViewById(R.id.homeButton);
        Button searchButton = findViewById(R.id.seachButton);
        Button addPostButton = findViewById(R.id.newPostButton);
        Button marketplaceButton = findViewById(R.id.marketPlaceButton);
        Button profileButton = findViewById(R.id.profileButton);




    }

    public void homeButtonClicked(View view){
        if (onOthersProfileActivity == true){
            onOthersProfileActivity = false;
        }
        else if (onOthersProfileActivity == false){
            startActivity(new Intent(OthersProfileActivity.this, MainActivity.class));
        }
    }

    public void searchButtonClicked(View view){
        startActivity(new Intent(OthersProfileActivity.this, SearchActivity.class));
    }

    public void addPostButtonClicked(View view){
        startActivity(new Intent(OthersProfileActivity.this, AddPostActivity.class));
    }

    public void marketplaceButtonClicked(View view){
        startActivity(new Intent(OthersProfileActivity.this, MarketplaceActivity.class));
    }

    public void profileButtonClicked(View view){
        startActivity(new Intent(OthersProfileActivity.this, ProfileActivity.class));
    }
}