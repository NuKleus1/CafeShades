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
import com.example.cafeshades.models.ItemModelClass;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    final ArrayList<ItemModelClass> itemModelClassArrayList;
    private final Context context;
    private final String TAG = "RECYCLE_VIEW_ADAPTER";
    DatabaseHelper db;


    public RecycleViewAdapter(ArrayList<ItemModelClass> itemModelClassArrayList, Context context) {
        this.itemModelClassArrayList = itemModelClassArrayList;
        this.context = context;
        db = DatabaseHelper.getInstance(context);
    }

    @NotNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_view_items, parent, false);
        return new RecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemModelClass item = itemModelClassArrayList.get(position);

//        holder.ivItemImage.setImageBitmap(itemModelClassArrayList.get(position).getItemImage());
        holder.tvItemName.setText(item.getItemName());
        holder.tvItemDesc.setText(item.getItemDescription());
        holder.cbFavourite.setChecked(item.isItemFavourite());

        int quantity = item.getItemQuantity();
        if (quantity == 0) {
            holder.disableAddQuantityMode();
            holder.tvItemPrice.setText(String.valueOf(item.getItemPrice()));
        } else {
            holder.enableAddQuantityMode();
            holder.tvItemQuantity.setText(String.valueOf(quantity));
            holder.tvItemPrice.setText(String.valueOf(item.getItemPrice() * quantity));
        }

        // Set Favourite to true in model class item when checked, and insert into DB
        // when unchecked, delete the item from the DB
        holder.cbFavourite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (position != -1) {
                if (isChecked) {
                    item.setItemFavourite(true);
                    db.setFavourite(item.getItemID(), 1);
                } else {
                    db.setFavourite(item.getItemID(), 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemModelClassArrayList.size();
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
            ItemModelClass item = itemModelClassArrayList.get(position);
            int itemID = item.getItemID();


            if (id == btnAdd.getId()) {
                tvItemQuantity.setText("1");
                item.setItemQuantity(1);
                db.setQuantity(itemID, 1);
                enableAddQuantityMode();
            } else if (id == btnItemAddQuantity.getId()) {

                int quantity = Integer.parseInt(tvItemQuantity.getText().toString()) + 1;
                item.setItemQuantity(quantity);

                tvItemQuantity.setText(String.valueOf(quantity));
                tvItemPrice.setText(String.valueOf(quantity * item.getItemPrice()));

                db.setQuantity(itemID, quantity);

            } else if (id == btnItemSubtractQuantity.getId()) {

                int quantity = Integer.parseInt(tvItemQuantity.getText().toString()) - 1;
                item.setItemQuantity(quantity);

                if (quantity == 0) {
                    tvItemQuantity.setText("0");
                    disableAddQuantityMode();
                } else {
                    tvItemQuantity.setText(String.valueOf(quantity));
                    tvItemPrice.setText(String.valueOf(quantity * item.getItemPrice()));
                }

                db.setQuantity(itemID, quantity);
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
