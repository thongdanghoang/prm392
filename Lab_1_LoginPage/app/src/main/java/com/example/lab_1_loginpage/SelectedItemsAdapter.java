package com.example.lab_1_loginpage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SelectedItemsAdapter extends RecyclerView.Adapter<SelectedItemsAdapter.ViewHolder> {

    private List<String> selectedItems;

    public SelectedItemsAdapter(List<String> selectedItems) {
        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTextView.setText(selectedItems.get(position));
    }

    @Override
    public int getItemCount() {
        return selectedItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}
