package com.example.uniproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private Button loginButton, registerButton;
    private EditText emailField, passwordField;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize views and Firebase Auth
        auth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.signinButton);
        registerButton = findViewById(R.id.registerNavButton);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        loginButton.setOnClickListener(view -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (validateInput(email, password)) {
                signInUser(email, password);
            } else {
                Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("LoginActivity", "Error navigating to Register", e);
            }
        });
    }

    private boolean validateInput(String email, String password) {
        return email.length() > 2 && password.length() > 6;
    }

    private void signInUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(login.this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(login.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
