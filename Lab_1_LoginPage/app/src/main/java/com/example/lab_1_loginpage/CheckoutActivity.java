package com.example.lab_1_loginpage;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DATA.DatabaseHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Data.Food;
import Data.Order;

public class CheckoutActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText tableNumberEditText;
    private EditText floorNumberEditText;
    private TextView totalPriceTextView;
    private RecyclerView itemsRecyclerView;
    private Button submitButton;
    private EditText timeEditText;
    private ArrayList<Food> selectedItems;
    private double totalPrice = 0.00;
    private DatabaseHelper databaseHelper;
    private String userId;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hiển thị nút quay lại trên Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back); // Đặt icon tùy chỉnh (nếu cần)
        }

        // Xử lý sự kiện khi nhấn nút quay lại
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Liên kết các thành phần trong layout với các biến
        nameEditText = findViewById(R.id.name);
        phoneEditText = findViewById(R.id.phone);
        tableNumberEditText = findViewById(R.id.table_number);
        floorNumberEditText = findViewById(R.id.floor_number);
        totalPriceTextView = findViewById(R.id.price);
        itemsRecyclerView = findViewById(R.id.items_recycler);
        submitButton = findViewById(R.id.submit_button);
        timeEditText = findViewById(R.id.time);

        // Lấy token từ Intent
        token = getIntent().getStringExtra("TOKEN");

        if (token != null) {
            parseToken(token);
        }

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Nhận danh sách món ăn đã chọn từ Intent
        selectedItems = getIntent().getParcelableArrayListExtra("SELECTED_ITEMS");

        timeEditText.setOnClickListener(v -> showTimePickerDialog());

        // Nhận thông tin Tầng và Tên bàn từ Intent
        String tableName = getIntent().getStringExtra("TABLE_NAME");
        int floorNumber = getIntent().getIntExtra("FLOOR_NUMBER", 0);

        // Hiển thị thông tin Tầng và Tên bàn trong các EditText
        if (tableName != null) {
            tableNumberEditText.setText(tableName);
        }
        floorNumberEditText.setText(String.valueOf(floorNumber));

        // Cài đặt RecyclerView
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemsAdapter adapter = new ItemsAdapter(selectedItems);
        itemsRecyclerView.setAdapter(adapter);

        // Cập nhật tổng giá
        updateTotalPrice();

        // Xử lý sự kiện khi bấm nút "Submit Order"
        submitButton.setOnClickListener(v -> submitOrder());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Xử lý khi nhấn nút quay lại
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Hàm cập nhật tổng giá
    private void updateTotalPrice() {
        totalPrice = 0;
        for (Food food : selectedItems) {
            totalPrice += food.getPrice();
        }
        totalPriceTextView.setText("Total: $" + String.format("%.2f", totalPrice));
    }

    private void submitOrder() {
        String userName = nameEditText.getText().toString();
        String phoneNumber = phoneEditText.getText().toString();
        String tableNumber = tableNumberEditText.getText().toString();
        String floorNumber = floorNumberEditText.getText().toString();
        String time = timeEditText.getText().toString();
        String orderId = "ORD" + getCurrentTime(); // Tạo mã đơn hàng duy nhất
        List<String> itemList = new ArrayList<>();

        for (Food food : selectedItems) {
            itemList.add(food.getName());
        }

        if (userName.isEmpty() || phoneNumber.isEmpty() || tableNumber.isEmpty() || floorNumber.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu đơn hàng vào cơ sở dữ liệu với tất cả các thông tin
        Order order = new Order(
                orderId,
                userId,
                userName,
                phoneNumber,
                tableNumber,
                floorNumber,
                itemList,
                time,
                totalPrice,
                "Đã hoàn thành",
                getCurrentTime());
        databaseHelper.addOrder(order);

        Toast.makeText(this, "Order saved successfully", Toast.LENGTH_SHORT).show();

        // Khởi tạo Intent để mở PayPalPaymentActivity
        Intent intent = new Intent(CheckoutActivity.this, PayPalPaymentActivity.class);
        intent.putExtra("TOTAL_PRICE", totalPrice);
        startActivity(intent);
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void showTimePickerDialog() {
        // Lấy giờ hiện tại
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Hiển thị TimePickerDialog với giới hạn từ 9:00 AM đến 10:00 PM
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    if (selectedHour >= 9 && selectedHour <= 22) {
                        String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        timeEditText.setText(formattedTime);
                    } else {
                        Toast.makeText(this, "Please select time between 9:00 AM and 10:00 PM", Toast.LENGTH_SHORT).show();
                    }
                }, hour, minute, false);

        timePickerDialog.show();
    }

    private void parseToken(String token) {
        try {
            // Tách token theo dấu '.'
            String[] splitToken = token.split("\\.");
            if (splitToken.length > 1) {
                // Giải mã phần body của token (phần thứ 2)
                String body = new String(Base64.decode(splitToken[1], Base64.DEFAULT));
                JSONObject jsonObject = new JSONObject(body);
                // Lấy userId từ phần body của token
                userId = jsonObject.getString("id");
                Log.d("CheckoutActivity", "User ID: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse token", Toast.LENGTH_SHORT).show();
        }
    }
}
