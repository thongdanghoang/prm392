package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    private static final int REDIRECT_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        TextView tvSuccessMessage = findViewById(R.id.tvSuccessMessage);
        Button btnContinue = findViewById(R.id.btnContinue);


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToMainScreen();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectToMainScreen();
            }
        }, REDIRECT_DELAY);
    }

    private void redirectToMainScreen() {
        Intent intent = new Intent(SuccessActivity.this, MainScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
