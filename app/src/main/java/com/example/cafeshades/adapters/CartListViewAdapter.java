package com.example.cafeshades.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartListViewAdapter extends RecyclerView.Adapter<CartListViewAdapter.CartListViewHolder> {

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CartListViewHolder extends RecyclerView.ViewHolder {

        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
