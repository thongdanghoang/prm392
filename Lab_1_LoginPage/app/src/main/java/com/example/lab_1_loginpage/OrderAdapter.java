package com.example.lab_1_loginpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Data.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderName.setText(order.getName());
        holder.orderPrice.setText("$" + String.format("%.2f", order.getPrice()));
        holder.orderStatus.setText("Status: " + order.getStatus());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderName, orderPrice, orderStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.order_name);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderStatus = itemView.findViewById(R.id.order_status);
        }
    }
}
