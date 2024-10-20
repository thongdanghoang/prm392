package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Data.Food;

public class CheckoutActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText tableNumberEditText;
    private EditText floorNumberEditText;
    private TextView totalPriceTextView;
    private RecyclerView itemsRecyclerView;
    private Button submitButton;

    private ArrayList<Food> selectedItems;
    private double totalPrice = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Liên kết các thành phần trong layout với các biến
        nameEditText = findViewById(R.id.name);
        phoneEditText = findViewById(R.id.phone);
        tableNumberEditText = findViewById(R.id.table_number);
        floorNumberEditText = findViewById(R.id.floor_number);
        totalPriceTextView = findViewById(R.id.price);
        itemsRecyclerView = findViewById(R.id.items_recycler);
        submitButton = findViewById(R.id.submit_button);

        // Nhận danh sách món ăn đã chọn từ Intent
        selectedItems = getIntent().getParcelableArrayListExtra("SELECTED_ITEMS");

        // Nhận thông tin Tầng và Tên bàn từ Intent
        String tableName = getIntent().getStringExtra("TABLE_NAME");
        int floorNumber = getIntent().getIntExtra("FLOOR_NUMBER", 0); // Nhận số tầng là kiểu int

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

    // Hàm tạo chuỗi thời gian hiện tại
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return sdf.format(new Date());
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
        // Khởi tạo Intent để mở PayPalPaymentActivity
        Intent intent = new Intent(CheckoutActivity.this, PayPalPaymentActivity.class);

        // Truyền tổng giá vào PayPalPaymentActivity
        intent.putExtra("TOTAL_PRICE", totalPrice);

        // Chuyển sang PayPalPaymentActivity
        startActivity(intent);
    }



    // Hàm reset form sau khi submit thành công
    private void clearForm() {
        nameEditText.setText("");
        phoneEditText.setText("");
        tableNumberEditText.setText("");
        floorNumberEditText.setText("");
        totalPriceTextView.setText("Total: $0.00");
        selectedItems.clear();
        itemsRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
