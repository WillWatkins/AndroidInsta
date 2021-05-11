package com.williamwatkins.androidinsta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MarketplaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);

        Button homeButton = findViewById(R.id.homeButton);
        Button searchButton = findViewById(R.id.seachButton);
        Button addPostButton = findViewById(R.id.newPostButton);
        Button marketplaceButton = findViewById(R.id.marketPlaceButton);
        Button profileButton = findViewById(R.id.profileButton);
    }

    public void homeButtonClicked(View view){
        startActivity(new Intent(MarketplaceActivity.this, MainActivity.class));
    }

    public void searchButtonClicked(View view){
        startActivity(new Intent(MarketplaceActivity.this, SearchActivity.class));
    }

    public void addPostButtonClicked(View view){
        startActivity(new Intent(MarketplaceActivity.this, AddPostActivity.class));
    }

    public void marketplaceButtonClicked(View view){
        startActivity(new Intent(MarketplaceActivity.this, MarketplaceActivity.class));
    }

    public void profileButtonClicked(View view){
        startActivity(new Intent(MarketplaceActivity.this, ProfileActivity.class));
    }
}