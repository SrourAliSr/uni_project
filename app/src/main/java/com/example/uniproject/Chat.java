package com.example.uniproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {
    private String contactName;
    private EditText messageInput;
    private ImageButton sendButton;
    private ListView chatList;
    private ArrayList<String> messages;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        contactName = getIntent().getStringExtra("contactName");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(contactName);
        }

        chatList = findViewById(R.id.chatList);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        messages = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        chatList.setAdapter(adapter);

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                messages.add("You: " + message);
                adapter.notifyDataSetChanged();
                messageInput.setText("");

                chatList.postDelayed(() -> {
                    messages.add(contactName + ": " + "This is an automated reply.");
                    adapter.notifyDataSetChanged();
                }, 1000);
            }
        });
    }
}
