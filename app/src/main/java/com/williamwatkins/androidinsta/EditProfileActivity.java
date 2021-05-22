package com.williamwatkins.androidinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    Button cancelButton;
    Button saveChangesButton;
    Button changeProfilePhotoButton;
    TextView changeNameTextView;
    TextView changeUsernameTextView;
    TextView changePronounsTextView;
    TextView changeWebsiteTextView;
    TextView changeBioTextView;
    ImageView profilePhotoImageView;

    String name;
    String username;
    String pronouns;
    String website;
    String bio;


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    String currentUserID = firebaseAuth.getCurrentUser().getUid();
    DatabaseReference profileSettingsReference = firebaseDatabase.getReference("user_account_settings").child(currentUserID);

    UserProfileDetails updatedUserProfileDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        cancelButton = findViewById(R.id.cancelButton);
        saveChangesButton = findViewById(R.id.saveChangesButton);
        changeProfilePhotoButton = findViewById(R.id.changeProfilePhoto);
        changeNameTextView = findViewById(R.id.editNameTextView);
        changeUsernameTextView = findViewById(R.id.editUsernameTextView);
        changePronounsTextView = findViewById(R.id.editPronounTextView);
        changeWebsiteTextView = findViewById(R.id.editWebsiteTextView);
        changeBioTextView = findViewById(R.id.editBioTextView);
        profilePhotoImageView = findViewById(R.id.profilePhotoImageView);


        //On create, it updates the edit profile activity with the current users values to show what they currently have as their profile inputs.
        profileSettingsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("displayName").getValue().toString();
                username = snapshot.child("username").getValue().toString();
                pronouns = snapshot.child("pronouns").getValue().toString();
                website = snapshot.child("website").getValue().toString();
                bio = snapshot.child("bio").getValue().toString();

                changeNameTextView.setText(name);
                changeUsernameTextView.setText(username);
                changePronounsTextView.setText(pronouns);
                changeWebsiteTextView.setText(website);
                changeBioTextView.setText(bio);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    //Takes the users changes, updates the firebase database with the new values and then changes the activity to the profile Activity.
    public void saveChanges(){

        name = changeNameTextView.getText().toString();
        username = changeUsernameTextView.getText().toString();
        pronouns = changePronounsTextView.getText().toString();
        website = changeWebsiteTextView.getText().toString();
        bio = changeBioTextView.getText().toString();

        if (name.isEmpty()) {
            name = "null";
        }

        //updatedUserProfileDetails = new UserProfileDetails(name, username, pronouns, website, bio);

        profileSettingsReference.child("displayName").setValue(name);
        profileSettingsReference.child("username").setValue(username);
        profileSettingsReference.child("pronouns").setValue(pronouns);
        profileSettingsReference.child("website").setValue(website);
        profileSettingsReference.child("bio").setValue(bio);


        startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
    }
}