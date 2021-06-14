package com.williamwatkins.androidinsta.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.williamwatkins.androidinsta.R;
import com.williamwatkins.androidinsta.models.User;
import com.williamwatkins.androidinsta.models.UsersPost;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPostActivity extends AppCompatActivity {

    //Firebase database references
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference usernameReference = firebaseDatabase.getReference("users_content");
    DatabaseReference usersDetails;

    //Firebase Auth
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    String userID = currentUser.getUid();

    //Firebase Storage
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference().child("users_posts_images");

    TextInputEditText captionText;
    Button shareButton;

    User users;
    String username;
    UsersPost post;
    String key = usernameReference.child(userID).push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        captionText = findViewById(R.id.captionText);
        shareButton = findViewById(R.id.shareButton);

        //Checks for permission to access photos.
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getPhoto();
        }

        usersDetails = firebaseDatabase.getReference("registered_users").child(userID);
//        String key = usernameReference.child(userID).push().getKey();
//
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrieves the current users username from firebase to add to the post.
                // Takes the users input for a post caption and adds them to the users content on firebase to be retrieved later
                usersDetails.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users = snapshot.getValue(User.class);
                        username = users.getUsername();
                        post = new UsersPost(username, captionText.getText().toString(), key, userID);

                        usernameReference.child(userID).child(key).setValue(post);
                        startActivity(new Intent(AddPostActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void getPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 ){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getPhoto();
            }
        }
    }


    //NEEDS TO BE MOVED WITHIN THE SHAREBUTTON ONCLICK, It is currently uploading the image as soon as it is selected from the images folder
    //rather than uploading it when the user clicks the share button- if the user decides not to create the post, the image will still
    //be uploaded rather than cancelled.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                ImageView imageView = findViewById(R.id.addPostImageView);
                imageView.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] data1 = stream.toByteArray();

                UploadTask uploadTask = storageReference.child(userID).child(key).putBytes(data1);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Upload failed: " + e, Toast.LENGTH_LONG);
                        System.out.println("Upload failed: " + e);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata();
                    }
                });



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        else {
//            startActivity(new Intent(AddPostActivity.this, MainActivity.class));
//        }
    }
}