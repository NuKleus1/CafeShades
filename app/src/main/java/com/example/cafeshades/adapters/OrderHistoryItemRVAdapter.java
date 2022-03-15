package com.example.cafeshades.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.R;
import com.example.cafeshades.models.OrderItemModelClass;

import java.util.ArrayList;

public class OrderHistoryItemRVAdapter extends RecyclerView.Adapter<OrderHistoryItemRVAdapter.OrderHistoryItemListViewHolder> {
    private ArrayList<OrderItemModelClass> orderItemModelClassArrayList;

    public OrderHistoryItemRVAdapter(ArrayList<OrderItemModelClass> orderItemModelClassArrayList) {
        this.orderItemModelClassArrayList = orderItemModelClassArrayList;
    }

    @NonNull
    @Override
    public OrderHistoryItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_list_items, parent, false);
        return new OrderHistoryItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryItemListViewHolder holder, int position) {
        holder.tvName.setText(orderItemModelClassArrayList.get(position).getItemName());
        holder.tvQuantity.setText(orderItemModelClassArrayList.get(position).getQuantity());
        holder.tvPrice.setText(orderItemModelClassArrayList.get(position).getItemPrice());
    }

    @Override
    public int getItemCount() {
        return orderItemModelClassArrayList.size();
    }

    public class OrderHistoryItemListViewHolder extends RecyclerView.ViewHolder {
        View v;
        TextView tvQuantity, tvName, tvPrice;

        public OrderHistoryItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;
            init();
        }

        private void init() {
            tvQuantity = v.findViewById(R.id.tv_item_quantity);
            tvName = v.findViewById(R.id.tv_item_name);
            tvPrice = v.findViewById(R.id.tv_item_price);
        }


    }
}
