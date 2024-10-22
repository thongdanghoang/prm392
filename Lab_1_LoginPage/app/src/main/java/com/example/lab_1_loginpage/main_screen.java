package com.example.lab_1_loginpage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.API.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.Abstract.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Data.Table;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class main_screen extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView restaurantRecyclerView;
    private RestaurantAdapter adapter;
    private List<Table> restaurantList;
    private BottomNavigationView bottomNavigationView;
    private static final String BASE_URL = "http://192.168.56.1:7225/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);


        // Khởi tạo Toolbar và đặt làm ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Nhận userId và token từ Intent
        final String userId = getIntent().getStringExtra("USER_ID");
        final String token = getIntent().getStringExtra("TOKEN");
        // Lưu token vào SharedPreferences
        if (token != null) {
            saveTokenToPreferences(token);
        }

        // Lấy token từ SharedPreferences nếu cần
        final String savedToken = getTokenFromPreferences();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Khởi tạo RecyclerView
        restaurantRecyclerView = findViewById(R.id.RerestaurantList);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Thiết lập Retrofit
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
//                    Toast.makeText(main_screen.this, "Failed to load data, using dummy data", Toast.LENGTH_SHORT).show();
                    restaurantList = createDummyTableList();
                }
                adapter = new RestaurantAdapter(main_screen.this, restaurantList, savedToken); // Truyền token vào adapter
                restaurantRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
//                Toast.makeText(main_screen.this, "Error: " + t.getMessage() + ", using dummy data", Toast.LENGTH_SHORT).show();
                restaurantList = createDummyTableList();
                adapter = new RestaurantAdapter(main_screen.this, restaurantList, savedToken); // Truyền token vào adapter
                restaurantRecyclerView.setAdapter(adapter);
            }
        });

        // Khởi tạo Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                Toast.makeText(main_screen.this, "Home", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.navigation_order) {
                // Thêm log để kiểm tra userId và token trước khi truyền

                // Chuyển sang OrderHistoryActivity với userId và token
                Intent intent = new Intent(main_screen.this, OrderHistoryActivity.class);
                intent.putExtra("USER_ID", userId); // Truyền userId qua Intent
                intent.putExtra("TOKEN", savedToken); // Truyền token qua Intent
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.navigation_chat) {
                Intent intent = new Intent(main_screen.this, ChatActivity.class);
                intent.putExtra("TOKEN", savedToken);
                startActivity(intent);
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Ánh xạ file menu vào Activity
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            // Xóa thông tin đăng nhập từ SharedPreferences
            clearTokenFromPreferences();

            // Thông báo đăng xuất
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

            // Chuyển về màn hình đăng nhập
            Intent intent = new Intent(this, login_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack hiện tại
            startActivity(intent);
            finish(); // Đóng activity hiện tại
            return true;
        } else if (item.getItemId() == R.id.menu_exit) {
            showExitDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showExitDialog() {
        // Hiển thị hộp thoại xác nhận thoát ứng dụng
        new AlertDialog.Builder(this)
                .setTitle("Thoát ứng dụng")
                .setMessage("Bạn có chắc chắn muốn thoát không?")
                .setPositiveButton("Có", (dialog, which) -> finish())
                .setNegativeButton("Không", null)
                .show();
    }

    // Phương thức lưu token vào SharedPreferences
    private void saveTokenToPreferences(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TOKEN", token);
        editor.apply(); // Lưu thay đổi
    }

    // Phương thức lấy token từ SharedPreferences
    private String getTokenFromPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("TOKEN", null); // Trả về null nếu không có token
    }

    // Phương thức xóa token khỏi SharedPreferences
    private void clearTokenFromPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("TOKEN");
        editor.apply(); // Lưu thay đổi
    }

    // Phương thức tạo danh sách Table mẫu
    private List<Table> createDummyTableList() {
        return new ArrayList<>(Arrays.asList(
                new Table("Table 1", "Thu Duc City", "9:00 AM - 10:00 PM", R.drawable.restaurant_a_image, 1, 4),
                new Table("Table 2", "Thu Duc City", "10:00 AM - 9:00 PM", R.drawable.restaurant_b_image, 2, 6),
                new Table("Table 3", "Thu Duc City", "9:00 AM - 10:00 PM", R.drawable.restaurant_a_image, 1, 2),
                new Table("Table 4", "Thu Duc City", "10:00 AM - 9:00 PM", R.drawable.restaurant_b_image, 2, 5)
        ));
    }

    @Override
    protected void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(main_screen.this, targetActivity);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Tọa độ của thành phố Thủ Đức
        LatLng thuDuc = new LatLng(10.8231, 106.7699);
        mMap.addMarker(new MarkerOptions().position(thuDuc).title("Marker in Thu Duc"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thuDuc, 12));
    }
}
