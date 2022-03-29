package com.example.cafeshades.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.Helper.DatabaseHelper;
import com.example.cafeshades.R;
import com.example.cafeshades.models.Product;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFavouriteRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_CATEGORY = 1;
    private static final int ITEM_VIEW_TYPE_PRODUCT = 2;
    private final Context context;
    private final String TAG = "RECYCLE_VIEW_ADAPTER";
    DatabaseHelper db;
    private ArrayList<Product> productArrayList;


    public HomeFavouriteRVAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
        db = DatabaseHelper.getInstance(context);
    }

    public void setData(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_PRODUCT) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.card_view_items, parent, false);
            return new ProductViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);
            return new CategoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holders, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_PRODUCT) {
            ProductViewHolder holder = (ProductViewHolder) holders;
            Product item = productArrayList.get(position);
            Picasso.get().load("http://mrcafe.mysandboxsite.com/images/" + item.getProductImage()).into(holder.ivItemImage);
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
        } else {
            CategoryViewHolder holder = (CategoryViewHolder) holders;
            holder.tvCategory.setText(productArrayList.get(position).getProductCategory());
        }
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (productArrayList.get(position).getProductId().isEmpty()) {
            return ITEM_VIEW_TYPE_CATEGORY;
        } else {
            return ITEM_VIEW_TYPE_PRODUCT;
        }

    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivItemImage;
        View v;
        MaterialTextView tvItemName, tvItemPrice, tvItemQuantity;
        Button btnItemAddQuantity, btnItemSubtractQuantity, btnAdd;
        MaterialCheckBox cbFavourite;

        public ProductViewHolder(View v) {
            super(v);
            this.v = v;
            init();
            setListener();

            Log.w(TAG, "VIEW HOLDER CREATED");
        }

        private void init() {

            ivItemImage = v.findViewById(R.id.iv_item_image);
            tvItemName = v.findViewById(R.id.tv_item_name);
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

            cbFavourite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    productArrayList.get(getAdapterPosition()).setProductFavourite(true);
                    db.setFavourite(productArrayList.get(getAdapterPosition()).getProductId(), 1);
                } else {
                    productArrayList.get(getAdapterPosition()).setProductFavourite(false);
                    db.setFavourite(productArrayList.get(getAdapterPosition()).getProductId(), 0);
                }
            });
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

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }
    }
}
