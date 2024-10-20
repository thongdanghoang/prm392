package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.Abstract.BaseActivity;

public class login_page extends BaseActivity{
    private Button login, signupButton;
    private EditText etUsername;
    private EditText etPassword;
    private final String REQUIRE = "Require";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        signupButton = findViewById(R.id.Signup);
        etUsername = findViewById(R.id.Login_EmailPhone);
        etPassword = findViewById(R.id.Login_PassWord);
        login = findViewById(R.id.Login_Button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpForm();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private boolean checkInput() {

        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError(REQUIRE);
            return false;
        }


        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(REQUIRE);
            return false;
        }


        return true;
    }

    private void signIn() {

        if (!checkInput()) {
            return;
        }

        navigateTo(main_screen.class);
        finish();
    }

    private void signUpForm() {
        navigateTo(signup_page.class);
        finish();
    }



    @Override
    protected void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(login_page.this, targetActivity);
        startActivity(intent);
    }

}