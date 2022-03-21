package com.example.cafeshades.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.Helper.DatabaseHelper;
import com.example.cafeshades.R;
import com.example.cafeshades.models.Product;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Product> productArrayList;
    private final Context context;
    private final String TAG = "RECYCLE_VIEW_ADAPTER";
    DatabaseHelper db;


    public RecyclerViewAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
        db = DatabaseHelper.getInstance(context);
    }

    public void setData(ArrayList<Product> productArrayList){
        this.productArrayList = productArrayList;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_view_items, parent, false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product item = productArrayList.get(position);

//        holder.ivItemImage.setImageBitmap(itemModelClassArrayList.get(position).getItemImage());
        holder.tvItemName.setText(item.getProductName());
        holder.cbFavourite.setChecked(item.isProductFavourite());

        int quantity = item.getProductQuantity();
        if (quantity == 0) {
            holder.disableAddQuantityMode();
            holder.tvItemPrice.setText(String.valueOf(item.getProductPrice()));
        } else {
            holder.enableAddQuantityMode();
            holder.tvItemQuantity.setText(String.valueOf(quantity));
            holder.tvItemPrice.setText(String.valueOf(item.getProductPrice() * quantity));
        }

        // Set Favourite to true in model class item when checked, and insert into DB
        // when unchecked, delete the item from the DB
        holder.cbFavourite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    item.setProductFavourite(true);
                    db.setFavourite(item.getProductId(), 1);
                } else {
                    item.setProductFavourite(false);
                    db.setFavourite(item.getProductId(), 0);
                }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        ImageView ivItemImage;
        View v;
        MaterialTextView tvItemName, tvItemDesc, tvItemPrice, tvItemQuantity;
        Button btnItemAddQuantity, btnItemSubtractQuantity, btnAdd;
        MaterialCheckBox cbFavourite;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
            init();
            setListener();

            Log.w(TAG, "VIEW HOLDER CREATED");
        }

        private void init() {

//            ivItemImage = v.findViewById(R.id.iv_item_image);
            tvItemName = v.findViewById(R.id.tv_item_name);
            tvItemDesc = v.findViewById(R.id.tv_item_desc);
            tvItemPrice = v.findViewById(R.id.tv_item_price);
            tvItemQuantity = v.findViewById(R.id.tv_item_quantity);
            btnAdd = v.findViewById(R.id.btn_add);
            btnItemAddQuantity = v.findViewById(R.id.btn_card_item_add_quantity);
            btnItemSubtractQuantity = v.findViewById(R.id.btn_card_item_subtract_quantity);
            cbFavourite = v.findViewById(R.id.checkbox_item_favourite);
        }

        private void setListener() {
            btnItemAddQuantity.setOnClickListener(this);
            btnItemSubtractQuantity.setOnClickListener(this);
            btnAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            int id = view.getId();
            Product item = productArrayList.get(position);
            String itemID = item.getProductId();


            if (id == btnAdd.getId()) {
                tvItemQuantity.setText("1");
                item.setProductQuantity(1);
                db.setQuantity(Integer.parseInt(itemID), 1);
                enableAddQuantityMode();
            } else if (id == btnItemAddQuantity.getId()) {

                int quantity = Integer.parseInt(tvItemQuantity.getText().toString()) + 1;
                item.setProductQuantity(quantity);

                tvItemQuantity.setText(String.valueOf(quantity));
                tvItemPrice.setText(String.valueOf(quantity * item.getProductPrice()));

                db.setQuantity(Integer.parseInt(itemID), quantity);

            } else if (id == btnItemSubtractQuantity.getId()) {

                int quantity = Integer.parseInt(tvItemQuantity.getText().toString()) - 1;
                item.setProductQuantity(quantity);

                if (quantity == 0) {
                    tvItemQuantity.setText("0");
                    disableAddQuantityMode();
                } else {
                    tvItemQuantity.setText(String.valueOf(quantity));
                    tvItemPrice.setText(String.valueOf(quantity * item.getProductPrice()));
                }

                db.setQuantity(Integer.parseInt(itemID), quantity);
            }
        }

        private void disableAddQuantityMode() {
            btnItemAddQuantity.setVisibility(View.GONE);
            btnItemSubtractQuantity.setVisibility(View.GONE);
            tvItemQuantity.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
        }

        private void enableAddQuantityMode() {
            btnAdd.setVisibility(View.GONE);
            btnItemAddQuantity.setVisibility(View.VISIBLE);
            btnItemSubtractQuantity.setVisibility(View.VISIBLE);
            tvItemQuantity.setVisibility(View.VISIBLE);
        }
    }
}
