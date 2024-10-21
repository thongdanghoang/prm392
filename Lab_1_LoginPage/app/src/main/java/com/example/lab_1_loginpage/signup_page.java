package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.Abstract.BaseActivity;

public class signup_page extends BaseActivity {
    Button login;
    private EditText etUsername;
    private EditText Email;
    private EditText phoneNumber;
    private EditText etPassword;
    private Button btnSignUp;

    private final String REQUIRE = "Require";

    @Override
    protected void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(signup_page.this, targetActivity);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        login = findViewById(R.id.login);
        etUsername = findViewById(R.id.Username_text);
        etPassword = findViewById(R.id.Password_text);
        Email = findViewById(R.id.Email_text);
        phoneNumber = findViewById(R.id.Phone_text);
        btnSignUp = findViewById(R.id.signupButton);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInForm();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {

        if (!checkInput()) {
            return;
        }

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
        navigateTo(login_page.class);
        finish();
    }

    private void signInForm() {
        navigateTo(login_page.class);
        finish();
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

        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            phoneNumber.setError(REQUIRE);
            return false;
        }

        if (!TextUtils.equals(etPassword.getText().toString(), Email.getText().toString())) {
            Email.setError(REQUIRE);
            return false;
        }

        return true;
    }

}
