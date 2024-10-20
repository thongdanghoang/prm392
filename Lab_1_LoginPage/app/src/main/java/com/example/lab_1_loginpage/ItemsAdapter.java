package com.example.lab_1_loginpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Data.Food;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Food> selectedItems;

    public ItemsAdapter(List<Food> selectedItems) {
        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Food food = selectedItems.get(position);
        holder.foodName.setText(food.getName());
        holder.foodPrice.setText("$" + food.getPrice());
    }

    @Override
    public int getItemCount() {
        return selectedItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView foodName, foodPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name_selected);
            foodPrice = itemView.findViewById(R.id.food_price_selected);
        }
    }
}
