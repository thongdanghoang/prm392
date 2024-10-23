package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.API.UserApi;
import com.Abstract.BaseActivity;
import com.Client.UnsafeOkHttpClient;

import Data.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterPage extends BaseActivity {
    Button login;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignUp;
    private final String REQUIRE = "Require";

    private UserApi userApi;

    @Override
    protected void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(RegisterPage.this, targetActivity);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        login = findViewById(R.id.login);
        etUsername = findViewById(R.id.Username_text);
        etPassword = findViewById(R.id.Password_text);
        btnSignUp = findViewById(R.id.signupButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://52.175.19.146/") // URL của API
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient()) // Sử dụng OkHttpClient không an toàn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userApi = retrofit.create(UserApi.class);

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

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User(username, password);

        // Gọi API với Retrofit
        Call<Void> call = userApi.signUp(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterPage.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    navigateTo(LoginPage.class);
                    finish();
                } else {
                    Toast.makeText(RegisterPage.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterPage.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void signInForm() {
        navigateTo(LoginPage.class);
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

        return true;
    }
}
