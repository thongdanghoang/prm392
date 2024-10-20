package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    private static final int REDIRECT_DELAY = 3000; // Thời gian chờ 3 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        TextView tvSuccessMessage = findViewById(R.id.tvSuccessMessage);
        Button btnContinue = findViewById(R.id.btnContinue);

        // Thiết lập nút "Tiếp tục" để chuyển về main_screen
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToMainScreen();
            }
        });

        // Tự động chuyển hướng sau 3 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectToMainScreen();
            }
        }, REDIRECT_DELAY);
    }

    private void redirectToMainScreen() {
        Intent intent = new Intent(SuccessActivity.this, main_screen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
