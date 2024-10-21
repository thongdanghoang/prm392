package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messege_activity);

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
}
