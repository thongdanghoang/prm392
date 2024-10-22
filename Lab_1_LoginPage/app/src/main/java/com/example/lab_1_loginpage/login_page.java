package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.API.UserApi;
import com.Abstract.BaseActivity;
import com.Client.UnsafeOkHttpClient;
import com.google.firebase.FirebaseApp;

import org.json.JSONObject;

import Data.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login_page extends BaseActivity {
    private static final String TAG = "LoginPage";
    private Button login, signupButton;
    private EditText etUsername;
    private EditText etPassword;
    private final String REQUIRE = "Require";

    private UserApi userApi;
    private String userId; // Biến để lưu ID người dùng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        FirebaseApp.initializeApp(this);

        signupButton = findViewById(R.id.Signup);
        etUsername = findViewById(R.id.Login_EmailPhone);
        etPassword = findViewById(R.id.Login_PassWord);
        login = findViewById(R.id.Login_Button);

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://52.175.19.146/")
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userApi = retrofit.create(UserApi.class);

        signupButton.setOnClickListener(v -> signUpForm());

        login.setOnClickListener(v -> signIn());
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

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User(username, password);

        Call<ResponseBody> call = userApi.signIn(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Lấy token từ body của phản hồi
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String token = jsonObject.getString("token");
                        userId = getUserIdFromToken(token);
                        Log.d(TAG, "User ID: " + userId);

                        // Điều hướng đến main_screen với userId và token
                        Intent intent = new Intent(login_page.this, main_screen.class);
                        intent.putExtra("USER_ID", userId);
                        intent.putExtra("TOKEN", token);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(login_page.this, "Lỗi xử lý phản hồi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "Đăng nhập không thành công: " + response.code());
                    Toast.makeText(login_page.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Lỗi kết nối: ", t);
                Toast.makeText(login_page.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức giải mã token để lấy userId
    private String getUserIdFromToken(String token) {
        try {
            // Log để xem token đầy đủ
            Log.d(TAG, "Full Token: " + token);

            // Cắt token thành các phần (header, payload, signature)
            String[] splitToken = token.split("\\.");

            // Log từng phần của token để xem cách token được cắt
            Log.d(TAG, "Token Header: " + splitToken[0]);
            Log.d(TAG, "Token Payload: " + splitToken[1]);
            Log.d(TAG, "Token Signature: " + splitToken[2]);

            // Giải mã payload từ Base64
            String body = new String(Base64.decode(splitToken[1], Base64.DEFAULT));
            Log.d(TAG, "Decoded Payload: " + body);

            // Phân tích JSON từ payload để lấy userId
            JSONObject jsonObject = new JSONObject(body);
            String userId = jsonObject.getString("id");
            Log.d(TAG, "Extracted User ID: " + userId);

            return userId; // Trả về ID người dùng
        } catch (Exception e) {
            Log.e(TAG, "Failed to parse token: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
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
