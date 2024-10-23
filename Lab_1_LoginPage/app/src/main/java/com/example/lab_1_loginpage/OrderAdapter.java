package com.example.lab_1_loginpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import Data.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.userNameTextView.setText(order.getUserName());
        holder.totalPriceTextView.setText("Total: $" + String.format("%.2f", order.getTotalPrice()));
        holder.paymentStatusTextView.setText("Status: " + order.getPaymentStatus());
        holder.orderTimeTextView.setText("Order Time: " + order.getOrderTime());

        // Sự kiện nhấp vào đơn hàng
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailsActivity.class);
            intent.putExtra("order", order);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView, totalPriceTextView, paymentStatusTextView, orderTimeTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.user_name);
            totalPriceTextView = itemView.findViewById(R.id.total_price);
            paymentStatusTextView = itemView.findViewById(R.id.payment_status);
            orderTimeTextView = itemView.findViewById(R.id.order_time);
        }
    }
}
