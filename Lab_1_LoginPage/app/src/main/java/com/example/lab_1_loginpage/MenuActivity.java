package com.example.lab_1_loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Data.Food;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView foodRecyclerView;
    private Button checkoutButton;
    private List<Food> foodList = new ArrayList<>();
    private List<Food> selectedItems = new ArrayList<>(); // To store selected food items

    // Variables to hold table information received from RestaurantAdapter
    private String tableName;
    private String tableAddress;
    private int floorNumber;
    private int seatNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Nhận thông tin bàn từ Intent
        tableName = getIntent().getStringExtra("TABLE_NAME");
        tableAddress = getIntent().getStringExtra("TABLE_ADDRESS");
        floorNumber = getIntent().getIntExtra("FLOOR_NUMBER", 0);
        seatNumber = getIntent().getIntExtra("SEAT_NUMBER", 0);

        // Initialize RecyclerView for the food menu
        foodRecyclerView = findViewById(R.id.recycler_food_menu);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populate foodList with items (dummy data or real data)
        foodList.add(new Food("Pizza", 12.99, "Delicious pizza with cheese"));
        foodList.add(new Food("Burger", 8.99, "Juicy beef burger"));
        foodList.add(new Food("Pasta", 10.99, "Spaghetti with marinara sauce"));

        // Set up the adapter
        FoodMenuAdapter adapter = new FoodMenuAdapter(this, foodList, selectedItems);
        foodRecyclerView.setAdapter(adapter);

        // Initialize Checkout Button and set OnClickListener
        checkoutButton = findViewById(R.id.btn_checkout);
        checkoutButton.setOnClickListener(v -> {
            if (selectedItems.isEmpty()) {
                // Show a message if no items are selected
                Toast.makeText(MenuActivity.this, "Please select some items to checkout", Toast.LENGTH_SHORT).show();
            } else {
                // Navigate to CheckoutActivity and pass the selected items and table information
                Intent intent = new Intent(MenuActivity.this, CheckoutActivity.class);
                intent.putParcelableArrayListExtra("SELECTED_ITEMS", new ArrayList<>(selectedItems));
                intent.putExtra("TABLE_NAME", tableName);
                intent.putExtra("TABLE_ADDRESS", tableAddress);
                intent.putExtra("FLOOR_NUMBER", floorNumber);
                intent.putExtra("SEAT_NUMBER", seatNumber);
                startActivity(intent);
            }
        });
    }
}
