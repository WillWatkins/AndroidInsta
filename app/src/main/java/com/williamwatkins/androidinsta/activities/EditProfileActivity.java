package com.williamwatkins.androidinsta.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.williamwatkins.androidinsta.R;
import com.williamwatkins.androidinsta.models.UsersPost;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    String profilePhotoFileName;

    //Firebase Auth
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String currentUserID = firebaseAuth.getCurrentUser().getUid();

    //Firebase Database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference profileSettingsReference = firebaseDatabase.getReference("user_account_settings").child(currentUserID);

    //Firebase Storage
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference profilePhotoStorageReference = firebaseStorage.getReference().child("users_profile_photos");

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
                profilePhotoFileName = snapshot.child("profilePhoto").getValue().toString();

                changeNameTextView.setText(name);
                changeUsernameTextView.setText(username);
                changePronounsTextView.setText(pronouns);
                changeWebsiteTextView.setText(website);
                changeBioTextView.setText(bio);


                profilePhotoStorageReference.child(currentUserID).child(profilePhotoFileName).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        profilePhotoImageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println("Failure to retrieve image in Main Activity: " + exception);
                    }
                });
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

        changeProfilePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ChangeProfilePhotoActivity.class));
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

        profileSettingsReference.child("displayName").setValue(name);
        profileSettingsReference.child("username").setValue(username);
        profileSettingsReference.child("pronouns").setValue(pronouns);
        profileSettingsReference.child("website").setValue(website);
        profileSettingsReference.child("bio").setValue(bio);


        startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
    }
}