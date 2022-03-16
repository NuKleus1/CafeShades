package com.example.cafeshades.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.R;
import com.example.cafeshades.models.Product;

import java.util.List;

public class OrderHistoryItemRVAdapter extends RecyclerView.Adapter<OrderHistoryItemRVAdapter.OrderHistoryItemListViewHolder> {
    private List<Product> productList;

    public OrderHistoryItemRVAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public OrderHistoryItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_list_items, parent, false);
        return new OrderHistoryItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryItemListViewHolder holder, int position) {
        holder.tvName.setText(productList.get(position).getProductName());
        holder.tvQuantity.setText(String.valueOf(productList.get(position).getProductQuantity()));
        holder.tvPrice.setText(String.valueOf(productList.get(position).getProductPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
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
