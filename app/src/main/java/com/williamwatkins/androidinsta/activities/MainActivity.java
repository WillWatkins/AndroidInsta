package com.williamwatkins.androidinsta.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.williamwatkins.androidinsta.R;
import com.williamwatkins.androidinsta.adapters.FeedRecyclerViewAdapter;
import com.williamwatkins.androidinsta.models.UsersPost;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference usersPostsReference;

    //Firebase Auth
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    //Firebase Storage
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference().child("users_posts_images");

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
        usersPostsReference = firebaseDatabase.getReference().child("users_content");

        //Retrieves the posts from the users on the firebase database and adds them to a recycler view
        usersPostsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersPosts.clear();
                //Below loops through to the users
                for (DataSnapshot UserIDs: Objects.requireNonNull(snapshot).getChildren()){
                    //Below loops through each users posts and creates a UserPost object with the content for the post
                    for (DataSnapshot usersContent: Objects.requireNonNull(UserIDs).getChildren()){
                        String username = usersContent.child("username").getValue().toString();
                        String caption = usersContent.child("caption").getValue().toString();
                        String likes = usersContent.child("likes").getValue().toString();
                        String usersID = usersContent.child("usersID").getValue().toString();
                        String imageLabel = usersContent.child("imageLabel").getValue().toString();

                        storageReference.child(usersID).child(imageLabel).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {

                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                retrievedUser = new UsersPost(username, caption, likes, bitmap);

//                              Adds the retrieved user to the array
                                usersPosts.add(retrievedUser);

                                //Once retrieved the data from the database, updates the recycler view with the new array
                                feedRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                System.out.println("Failure to retrieve image in Main Activity: " + exception);
                            }
                        });
                    }
                }
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