package com.williamwatkins.androidinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    Button toRegister;
    private TextInputEditText login_email;
    private TextInputEditText login_password;
    ConstraintLayout backgroundLayout;

    //Firebase database login
    private FirebaseAuth firebaseAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        toRegister = findViewById(R.id.toRegister);
        login_email = findViewById(R.id.loginEmailInput);
        login_password = findViewById(R.id.loginPasswordInput);
        backgroundLayout = findViewById(R.id.backgroundLayout);

        backgroundLayout.setOnClickListener((View.OnClickListener) this);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (login_email.getText().toString() == null || login_password.getText().toString() == null){
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    Log.i("SIGN IN", "FAILED");
                } else {
                    signIn(login_email.getText().toString(), login_password.getText().toString());

                }
            }
        });

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Register.class));
            }
        });
    }


    public void signIn(String email, String password){


        if (email.matches("") || password.matches("")){

            Toast.makeText(getApplicationContext(), "A username and password are required", Toast.LENGTH_LONG).show();

        } else {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            hideSoftKeyBoard();
                            Log.d("Sign in: ", "SUCCESS");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Sign in: ", "FAILED", task.getException());
                            Toast.makeText(getApplicationContext(), "Sign in Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.backgroundLayout){
            hideSoftKeyBoard();
        }
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}