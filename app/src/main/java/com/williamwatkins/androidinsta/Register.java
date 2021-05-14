package com.williamwatkins.androidinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
Register Class is used to register a user to the database to create an account

 */

public class Register extends AppCompatActivity {

    private TextInputEditText reg_name;
    private TextInputEditText reg_surname;
    private TextInputEditText reg_username;
    private TextInputEditText reg_age;
    private TextInputEditText reg_email;
    private TextInputEditText reg_password;
    Button registerButton;
    Button toLoginActivity;

    //Firebase database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference registerUserReference = database.getReference("/registered_users");
    DatabaseReference createProfileReference = database.getReference("/user_account_settings");
    private FirebaseAuth firebaseAuth;

    String userID;
    User registerUser;
    UserProfileDetails newUserProfileDetails;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser != null){

            startActivity(new Intent(Register.this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_name = findViewById(R.id.nameInput);
        reg_surname = findViewById(R.id.surnameInput);
        reg_username = findViewById(R.id.usernameInput);
        reg_age = findViewById(R.id.ageInput);
        reg_email = findViewById(R.id.emailInput);
        reg_password = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);
        toLoginActivity = findViewById(R.id.toLoginActivity);

        firebaseAuth = FirebaseAuth.getInstance();

        //Creates a new user and adds them to the database.
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();

                if (reg_name.getText().toString().isEmpty() ||
                        reg_surname.getText().toString().isEmpty() ||
                        reg_username.getText().toString().isEmpty() ||
                        reg_age.getText().toString().isEmpty() ||
                        reg_email.getText().toString().isEmpty() ||
                        reg_password.getText().toString().isEmpty()) {
                    makeToastLong("Please complete all details for registration");

                } else {
                    String age = reg_age.getText().toString();

                    registerUser = new User(reg_name.getText().toString(),
                            reg_surname.getText().toString(),
                            reg_username.getText().toString(),
                            Integer.parseInt(age),
                            reg_email.getText().toString(),
                            "0");

                    newUserProfileDetails = new UserProfileDetails(registerUser.getUsername());


                    RegisterUser(reg_email.getText().toString(), reg_password.getText().toString());
                    }
            }
        });

        toLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, LoginActivity.class));
            }
        });

    }

    private void RegisterUser(String email, String password) {

        if (email.matches("") || password.matches("")){

            makeToastLong("A username and password are required");

        } else {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Create User", "SUCCESS");
                            makeToastLong("Registration successful");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            userID = user.getUid();
                            registerUser.setUser_id(userID);
                            System.out.println("USER ID:" + registerUser.user_id);
                            registerUserReference.child(userID).setValue(registerUser);
                            createProfileReference.child(userID).setValue(newUserProfileDetails);
                            startActivity(new Intent(Register.this, MainActivity.class));
                        } else {
                            Log.w("Create User", "FAILED", task.getException());
                            makeToastLong("Registration Failed: " + task.getException());
                        }
                    }
                });
        }

    }

    private void makeToastLong(String string){
        Toast.makeText(getApplicationContext(),string, Toast.LENGTH_LONG).show();
    }

    //Hides the keyboard
    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}