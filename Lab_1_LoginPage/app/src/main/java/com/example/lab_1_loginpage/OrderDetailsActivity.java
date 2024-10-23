package com.example.lab_1_loginpage;

import android.os.Bundle;
import android.view.MenuItem; // Import MenuItem
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import Data.Order;

public class OrderDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Order Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút quay lại
        }

        // Nhận thông tin Order từ Intent
        Order order = (Order) getIntent().getSerializableExtra("order");

        // Liên kết các TextView
        TextView orderIdTextView = findViewById(R.id.order_id);
        TextView userNameTextView = findViewById(R.id.user_name);
        TextView phoneNumberTextView = findViewById(R.id.phone_number);
        TextView tableNumberTextView = findViewById(R.id.table_number);
        TextView floorNumberTextView = findViewById(R.id.floor_number);
        TextView totalPriceTextView = findViewById(R.id.total_price);
        TextView paymentStatusTextView = findViewById(R.id.payment_status);
        TextView orderTimeTextView = findViewById(R.id.order_time);

        // Liên kết RecyclerView cho danh sách món đã chọn
        RecyclerView recyclerView = findViewById(R.id.selected_items_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Hiển thị thông tin đơn hàng
        if (order != null) {
            orderIdTextView.setText("Order ID: " + order.getId());
            userNameTextView.setText("Name: " + order.getUserName());
            phoneNumberTextView.setText("Phone Number: " + order.getPhoneNumber());
            tableNumberTextView.setText("Table Number: " + order.getTableNumber());
            floorNumberTextView.setText("Floor Number: " + order.getFloorNumber());
            totalPriceTextView.setText("Total: $" + String.format("%.2f", order.getTotalPrice()));
            paymentStatusTextView.setText("Status: " + order.getPaymentStatus());
            orderTimeTextView.setText("Order Time: " + order.getOrderTime());

            // Cài đặt Adapter cho RecyclerView để hiển thị danh sách món đã chọn
            List<String> selectedItems = order.getSelectedItems();
            SelectedItemsAdapter adapter = new SelectedItemsAdapter(selectedItems);
            recyclerView.setAdapter(adapter);
        }
    }

    // Xử lý sự kiện khi bấm nút Back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Quay về trang trước
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
