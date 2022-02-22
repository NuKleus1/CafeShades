package com.example.cafeshades.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.R;
import com.example.cafeshades.models.ItemModelClass;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartListViewHolder> {

    private static final String TAG = "CART_LIST_VIEW_ADAPTER";
    ArrayList<ItemModelClass> itemModelClassArrayList;
    OnViewClickListeners onViewClickListeners;

    public CartRecyclerViewAdapter(ArrayList<ItemModelClass> itemModelClassArrayList, OnViewClickListeners onViewClickListeners) {
        this.itemModelClassArrayList = itemModelClassArrayList;
        this.onViewClickListeners = onViewClickListeners;
    }


    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_cart_items, parent, false);
        return new CartListViewHolder(view, onViewClickListeners);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        ItemModelClass item = itemModelClassArrayList.get(position);

        holder.tvItemName.setText(item.getItemName());

        int quantity = item.getItemQuantity();
        holder.tvItemQuantity.setText(String.valueOf(quantity));
        holder.tvItemPrice.setText(String.valueOf(item.getItemPrice() * quantity));
        holder.tvBasePrice.setText(String.valueOf(item.getItemPrice()));
    }

    @Override
    public int getItemCount() {
        return itemModelClassArrayList.size();
    }

    public interface OnViewClickListeners {

        void setQuantity(int itemId, int quantity);

        void setTotal();

        void setCartEmptyHint();
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View v;
        MaterialTextView tvItemName, tvItemPrice, tvItemQuantity, tvBasePrice;
        Button btnItemAddQuantity, btnItemSubtractQuantity;
        OnViewClickListeners onViewClickListeners;

        public CartListViewHolder(@NonNull View v, OnViewClickListeners onViewClickListeners) {
            super(v);
            this.v = v;
            this.onViewClickListeners = onViewClickListeners;

            init();
            setListeners();
            Log.w(TAG, "VIEW HOLDER CREATED");

        }

        private void setListeners() {
            btnItemAddQuantity.setOnClickListener(this);
            btnItemSubtractQuantity.setOnClickListener(this);
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            int id = view.getId();
            ItemModelClass item = itemModelClassArrayList.get(position);
            int itemID = item.getItemID();

//            if (id == btnAdd.getId()) {
//                tvItemQuantity.setText("1");
//                item.setItemQuantity(1);
//                onViewClickListeners.setQuantity(itemID, 1);
//                enableAddQuantityMode();
//            } else
            if (id == btnItemAddQuantity.getId()) {
                int quantity = Integer.parseInt(tvItemQuantity.getText().toString()) + 1;
                item.setItemQuantity(quantity);
                tvItemQuantity.setText(String.valueOf(quantity));
                tvItemPrice.setText(String.valueOf(quantity * item.getItemPrice()));
                onViewClickListeners.setQuantity(itemID, quantity);
            } else if (id == btnItemSubtractQuantity.getId()) {
                int quantity = Integer.parseInt(tvItemQuantity.getText().toString()) - 1;
                onViewClickListeners.setQuantity(itemID, quantity);
                item.setItemQuantity(quantity);
                if (quantity == 0) {
//                    tvItemQuantity.setText("0");
//                    disableAddQuantityMode();
                    itemModelClassArrayList.remove(position);
                    notifyDataSetChanged();
                    if (itemModelClassArrayList.isEmpty()) {
                        onViewClickListeners.setCartEmptyHint();
                    }
                } else {
                    tvItemQuantity.setText(String.valueOf(quantity));
                    tvItemPrice.setText(String.valueOf(quantity * item.getItemPrice()));
                }
            }
            onViewClickListeners.setTotal();
        }

        private void init() {
            tvItemName = v.findViewById(R.id.tv_item_name);
            tvItemPrice = v.findViewById(R.id.tv_item_price);
            tvBasePrice = v.findViewById(R.id.tv_item_base_price);
            tvItemQuantity = v.findViewById(R.id.tv_item_quantity);
            btnItemAddQuantity = v.findViewById(R.id.btn_card_item_add_quantity);
            btnItemSubtractQuantity = v.findViewById(R.id.btn_card_item_subtract_quantity);
        }
    }

}
