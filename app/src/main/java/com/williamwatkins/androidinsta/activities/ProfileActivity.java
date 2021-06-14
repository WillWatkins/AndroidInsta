package com.williamwatkins.androidinsta.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.williamwatkins.androidinsta.Settings;
import com.williamwatkins.androidinsta.adapters.FeedRecyclerViewAdapter;
import com.williamwatkins.androidinsta.models.UserProfileDetails;
import com.williamwatkins.androidinsta.models.UsersPost;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    //Firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference userProfileReference;
    DatabaseReference usersPostsReference;

    //Firebase Auth
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    //Firebase Storage
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference().child("users_posts_images");
    //StorageReference profilePhotoReference = firebaseStorage.getReference().child("users_profile_photo");

    //Variables used to update the current users profile with their profile details and to interact with them.
    UserProfileDetails userProfileDetails;
    TextView profileUsername;
    TextView bio;
    Button profilePostsButton;
    Button profileFollowersButton;
    Button profileFollowingButton;

    FeedRecyclerViewAdapter feedRecyclerViewAdapter;
    UsersPost post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button homeButton = findViewById(R.id.homeButton);
        Button searchButton = findViewById(R.id.seachButton);
        Button addPostButton = findViewById(R.id.newPostButton);
        Button marketplaceButton = findViewById(R.id.marketPlaceButton);
        Button profileButton = findViewById(R.id.profileButton);
        Button editProfile = findViewById(R.id.editProfileButton);

        profileUsername = findViewById(R.id.profileUsername);
        bio = findViewById(R.id.bio);
        profilePostsButton = findViewById(R.id.profilePostsButton);
        profileFollowersButton = findViewById(R.id.profileFollowersButton);
        profileFollowingButton = findViewById(R.id.profileFollowingButton);

        //Firebase Auth- retrieves the current logged in users ID
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String currentUserID = currentUser.getUid();

        userProfileReference = firebaseDatabase.getReference().child("user_account_settings").child(currentUserID);

        //Retrieves the current users most up-to-date profile details from the Firebase Database
        //and then uses updateProfile() to populate the profile with this info.
        userProfileReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String displayName = snapshot.child("displayName").getValue().toString();
                String followerCount = snapshot.child("followerCount").getValue().toString();
                String followingCount = snapshot.child("followingCount").getValue().toString();
                String posts = snapshot.child("posts").getValue().toString();
                String profilePhoto= snapshot.child("profilePhoto").getValue().toString();
                String username = snapshot.child("username").getValue().toString();
                String website = snapshot.child("website").getValue().toString();
                String bio = snapshot.child("bio").getValue().toString();

                userProfileDetails = new UserProfileDetails(username, bio, displayName, followerCount, followingCount, posts, profilePhoto, website);

                updateProfile();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Set up for retrieving the users posts and the recycler view with a custom adapter to show the current users posts
        usersPostsReference = firebaseDatabase.getReference().child("users_content").child(currentUserID);
        RecyclerView feedRecyclerView = findViewById(R.id.usersPostsRecyclerView);
        ArrayList<UsersPost> usersPosts= new ArrayList<>();
        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter(this, usersPosts);
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Retrieves the current users posts from firebase database  nd adds them to the above recycler view
        usersPostsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot usersRetrievedPosts) {
                for (DataSnapshot postContent: Objects.requireNonNull(usersRetrievedPosts).getChildren()){
                    String username = postContent.child("username").getValue().toString();
                    String caption = postContent.child("caption").getValue().toString();
                    String likes = postContent.child("likes").getValue().toString();
                    String usersID = postContent.child("usersID").getValue().toString();
                    String imageLabel = postContent.child("imageLabel").getValue().toString();

                    //Retrieves the posts photo from Firebase Storage to be added to the post
                    storageReference.child(usersID).child(imageLabel).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {

                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            post = new UsersPost(username, caption, likes, bitmap);

                            //Adds the retrieved user to the array
                            usersPosts.add(post);

                            //Updates the recycler view with the new array
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Takes the user to the EditProfileActivity
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });
    }

    //Sets the profile with the most up-to-date info which will be retrieved from the Firebase Database
    public void updateProfile(){
        profileUsername.setText(userProfileDetails.getUsername());
        bio.setText(userProfileDetails.getBio());
        profilePostsButton.setText(userProfileDetails.getPosts());
        profileFollowersButton.setText(userProfileDetails.getFollowerCount());
        profileFollowingButton.setText(userProfileDetails.getFollowingCount());
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
                startActivity(new Intent(ProfileActivity.this, Settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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