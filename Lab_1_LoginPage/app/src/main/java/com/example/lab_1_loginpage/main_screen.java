package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.API.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.Abstract.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Data.Table;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class main_screen extends BaseActivity {

    private RecyclerView restaurantRecyclerView;
    private RestaurantAdapter adapter;
    private List<Table> restaurantList;
    private BottomNavigationView bottomNavigationView;
    private static final String BASE_URL = "http://192.168.56.1:7225/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        // Initialize RecyclerView
        restaurantRecyclerView = findViewById(R.id.RerestaurantList);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API
        apiService.getSeats().enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    restaurantList = response.body();
                } else {
                    Toast.makeText(main_screen.this, "Failed to load data, using dummy data", Toast.LENGTH_SHORT).show();
                    restaurantList = createDummyTableList(); // Sử dụng danh sách tạm khi thất bại
                }
                // Cài đặt adapter
                adapter = new RestaurantAdapter(main_screen.this, restaurantList);
                restaurantRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(main_screen.this, "Error: " + t.getMessage() + ", using dummy data", Toast.LENGTH_SHORT).show();
                restaurantList = createDummyTableList(); // Sử dụng danh sách tạm khi có lỗi
                adapter = new RestaurantAdapter(main_screen.this, restaurantList);
                restaurantRecyclerView.setAdapter(adapter);
            }
        });

        // Initialize Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    Toast.makeText(main_screen.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.navigation_order) {
                    Toast.makeText(main_screen.this, "Order", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.navigation_chat) {
                    // Chuyển sang màn hình ChatActivity
                    Intent intent = new Intent(main_screen.this, ChatActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    // Phương thức tạo danh sách Table mẫu
    private List<Table> createDummyTableList() {
        return new ArrayList<>(Arrays.asList(
                new Table("Table 1", "123 Main Street", "9:00 AM - 10:00 PM", R.drawable.restaurant_a_image, 1, 4),
                new Table("Table 2", "456 Elm Street", "10:00 AM - 9:00 PM", R.drawable.restaurant_b_image, 2, 6)
        ));
    }

    @Override
    protected void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(main_screen.this, targetActivity);
        startActivity(intent);
    }
}
