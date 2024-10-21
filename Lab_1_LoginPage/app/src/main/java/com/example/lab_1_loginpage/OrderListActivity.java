package com.example.lab_1_loginpage;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Data.Order;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list); // Đảm bảo layout đã được tạo

        orderRecyclerView = findViewById(R.id.recycler_order_list);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo danh sách đơn hàng mẫu
        orderList = new ArrayList<>();
        orderList.add(new Order("Order 1", 29.99, "Pending"));
        orderList.add(new Order("Order 2", 19.99, "Completed"));
        orderList.add(new Order("Order 3", 39.99, "In Progress"));

        // Set adapter cho RecyclerView
        orderAdapter = new OrderAdapter(orderList);
        orderRecyclerView.setAdapter(orderAdapter);
    }
}
