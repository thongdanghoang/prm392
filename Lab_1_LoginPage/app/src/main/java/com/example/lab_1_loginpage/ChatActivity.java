package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import Data.Message;

public class ChatActivity extends AppCompatActivity {

    private static final String USER_ID = "0f2ad7c0-55a3-436b-8803-ecb642308abc";
    private DatabaseReference messagesRef;
    private EditText messageInput;
    private LinearLayout messagesLayout;
    private String User_seen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messege_activity);

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        // Thiết lập Firebase
        messagesRef = FirebaseDatabase.getInstance().getReference("messages").child(USER_ID);

        // Liên kết các view
        messageInput = findViewById(R.id.message_input);
        messagesLayout = findViewById(R.id.messages_layout);
        ImageView sendButton = findViewById(R.id.send_button);

        String token = getIntent().getStringExtra("TOKEN");

        if (token != null) {
            parseToken(token);
        }
        // Xử lý khi bấm nút Gửi
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Thiết lập BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    // Chuyển sang màn hình main_screen
                    Intent intent = new Intent(ChatActivity.this, main_screen.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.navigation_order) {
                    Toast.makeText(ChatActivity.this, "Order", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.navigation_chat) {
                    Toast.makeText(ChatActivity.this, "Chat", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
    private void parseToken(String token) {
        try {
            String[] splitToken = token.split("\\.");
            String body = new String(Base64.decode(splitToken[1], Base64.DEFAULT));
            JSONObject jsonObject = new JSONObject(body);
            User_seen = jsonObject.getString("id");


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse token", Toast.LENGTH_SHORT).show();
        }
    }
    private void sendMessage() {
        String message = messageInput.getText().toString().trim();
        if (!TextUtils.isEmpty(message)) {
            // Tạo đối tượng tin nhắn
            String messageId = messagesRef.push().getKey();
            Message newMessage = new Message(User_seen, message);

            // Lưu tin nhắn vào Firebase
            if (messageId != null) {
                messagesRef.child(messageId).setValue(newMessage);
                messageInput.setText("");
            }
        }
    }
}
