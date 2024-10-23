package com.example.lab_1_loginpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Data.Food;

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.FoodViewHolder> {

    private Context context;
    private List<Food> foodList;
    private List<Food> selectedItems; // List to store selected items

    public FoodMenuAdapter(Context context, List<Food> foodList, List<Food> selectedItems) {
        this.context = context;
        this.foodList = foodList;
        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodName.setText(food.getName());
        holder.foodPrice.setText("$" + food.getPrice());

        // Reset listener to avoid incorrect behavior when views are reused
        holder.foodCheckbox.setOnCheckedChangeListener(null);

        // Set checkbox checked state based on whether the item is in the selected list
        holder.foodCheckbox.setChecked(selectedItems.contains(food));

        // Handle food item selection using the checkbox
        holder.foodCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(food);
            } else {
                selectedItems.remove(food);
            }
        });

        // Show description when the item is clicked
        holder.itemView.setOnClickListener(v -> showFoodDescriptionDialog(food));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    // Function to show the modal dialog with food description
    private void showFoodDescriptionDialog(Food food) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(food.getName());
        builder.setMessage("Description: " + food.getDescription() + "\n\nPrice: $" + food.getPrice());
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView foodName, foodPrice;
        CheckBox foodCheckbox;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            foodPrice = itemView.findViewById(R.id.food_price);
            foodCheckbox = itemView.findViewById(R.id.food_checkbox);
        }
    }
}
