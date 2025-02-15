package com.example.uniproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    private EditText email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth

        Button goToLoginButton = findViewById(R.id.go_to_login);
        Button registerButton = findViewById(R.id.registerButton);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.password);

        goToLoginButton.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        registerButton.setOnClickListener(view -> {
            try {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (emailText.length() > 2 && passwordText.length() > 6) {
                    signup(emailText, passwordText);
                } else {
                    Toast.makeText(register.this, "Email or password invalid", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void signup(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(register.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(register.this, login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
