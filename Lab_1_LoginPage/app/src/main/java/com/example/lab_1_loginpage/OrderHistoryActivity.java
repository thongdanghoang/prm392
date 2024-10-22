package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.DATA.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import Data.Order;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView orderHistoryRecyclerView;
    private OrderAdapter orderAdapter;
    private DatabaseHelper databaseHelper;
    private boolean isAdmin = false;
    private  String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back); // Đặt icon tùy chỉnh (nếu cần)
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Khởi tạo RecyclerView và DatabaseHelper
        orderHistoryRecyclerView = findViewById(R.id.order_history_recycler_view);
        orderHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);

        // Nhận token và userId từ Intent
        String token = getIntent().getStringExtra("TOKEN");

        if (token != null) {
            parseToken(token);
        }

        // Kiểm tra vai trò của người dùng
        if (isAdmin) {
            // Nếu là ADMIN, tải tất cả đơn hàng
            loadAllOrders();
        } else if (userId != null) {
            // Nếu không, tải đơn hàng theo userId
            loadOrderHistory(userId);
        } else {
            Toast.makeText(this, "Failed to extract user ID from token", Toast.LENGTH_SHORT).show();
        }

        // Khởi tạo Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                // Quay lại màn hình chính
                Intent intent = new Intent(OrderHistoryActivity.this, main_screen.class);
                intent.putExtra("TOKEN", token);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.navigation_order) {
                Toast.makeText(OrderHistoryActivity.this, "Already on Order History", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.navigation_chat) {
                Intent intent = new Intent(OrderHistoryActivity.this, ChatActivity.class);
                intent.putExtra("TOKEN", token);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    // Phân tích token để lấy vai trò
    private void parseToken(String token) {
        try {
            String[] splitToken = token.split("\\.");
            String body = new String(Base64.decode(splitToken[1], Base64.DEFAULT));
            JSONObject jsonObject = new JSONObject(body);
            userId = jsonObject.getString("id");
            // Kiểm tra vai trò từ token
            JSONArray rolesArray = jsonObject.getJSONArray("roles");
            for (int i = 0; i < rolesArray.length(); i++) {
                if ("ROLE_ADMIN".equals(rolesArray.getString(i))) {
                    isAdmin = true; // Đặt cờ ADMIN thành true
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse token", Toast.LENGTH_SHORT).show();
        }
    }

    // Load lịch sử đơn hàng từ SQLite theo userId
    // Load lịch sử đơn hàng từ SQLite theo userId
    private void loadOrderHistory(String userId) {
        List<Order> orderList = databaseHelper.getOrdersByUserId(userId);

        if (orderList.isEmpty()) {
            Toast.makeText(this, "No order history found for this user", Toast.LENGTH_SHORT).show();
        } else {
            orderAdapter = new OrderAdapter(this, orderList); // Truyền Context và List<Order>
            orderHistoryRecyclerView.setAdapter(orderAdapter);
        }
    }

    // Load tất cả các đơn hàng từ SQLite cho ADMIN
    private void loadAllOrders() {
        List<Order> orderList = databaseHelper.getAllOrders();

        if (orderList.isEmpty()) {
            Toast.makeText(this, "No order history found", Toast.LENGTH_SHORT).show();
        } else {
            orderAdapter = new OrderAdapter(this, orderList); // Truyền Context và List<Order>
            orderHistoryRecyclerView.setAdapter(orderAdapter);
        }
    }

}
