package com.example.lab_1_loginpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Data.Table;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<Table> restaurantList;
    private String token; // Thêm biến token

    public RestaurantAdapter(Context context, List<Table> restaurantList, String token) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.token = token; // Gán token
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lay_out_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Table table = restaurantList.get(position);
        holder.restaurantName.setText(table.getName());
        holder.restaurantAddress.setText(table.getAddress());
        holder.floorNumber.setText("Floor Number: " + table.getFloorNumber());
        holder.seatNumber.setText("Seats: " + table.getSeatNumber());
        holder.restaurantImage.setImageResource(table.getImageResourceId());

        // Set an OnClickListener for each item
        holder.itemView.setOnClickListener(v -> {
            // Navigate to MenuActivity when an item is clicked
            Intent intent = new Intent(context, MenuActivity.class);
            // Pass necessary information to MenuActivity
            intent.putExtra("TABLE_NAME", table.getName());
            intent.putExtra("TABLE_ADDRESS", table.getAddress());
            intent.putExtra("FLOOR_NUMBER", table.getFloorNumber());
            intent.putExtra("SEAT_NUMBER", table.getSeatNumber());
            intent.putExtra("TOKEN", token); // Truyền token qua Intent
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantName, restaurantAddress, floorNumber, seatNumber;
        ImageView restaurantImage;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantAddress = itemView.findViewById(R.id.restaurant_address);
            floorNumber = itemView.findViewById(R.id.floor_number);
            seatNumber = itemView.findViewById(R.id.seat_number);
            restaurantImage = itemView.findViewById(R.id.image_restaurant);
        }
    }
}

