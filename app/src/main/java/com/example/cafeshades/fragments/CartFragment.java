package com.example.cafeshades.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.Helper.DatabaseHelper;
import com.example.cafeshades.R;
import com.example.cafeshades.adapters.CartRecyclerViewAdapter;

public class CartFragment extends Fragment implements CartRecyclerViewAdapter.OnViewClickListeners {
    private static final String TAG = "CART_FRAGMENT";
    View v = null;
    RecyclerView recyclerView;
    CartRecyclerViewAdapter adapter;
    TextView tvItemTotal;
    TextView tvTaxesCharges;
    TextView tvGrandTotal;
    TextView tvCartEmptyHint;
    LinearLayout llCart;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_cart, container, false);
            Log.w(TAG, "onCreateViewNULL/Fragment_Cart");
            init();
            setData();
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getCartItemTotal() == 0) {
            setCartEmptyHint();
        } else {
            llCart.setVisibility(View.VISIBLE);
            tvCartEmptyHint.setVisibility(View.GONE);
        }
    }

    private void setData() {
//        adapter = new RecycleViewAdapter(DatabaseHelper.getInstance(getContext()).getAllCartItems(), getContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);

        adapter = new CartRecyclerViewAdapter(DatabaseHelper.getInstance(getContext()).getAllCartProducts(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        setTotal();
    }

    private void init() {
        recyclerView = v.findViewById(R.id.recycle_view_cart);
        tvItemTotal = v.findViewById(R.id.tv_item_total);
        tvTaxesCharges = v.findViewById(R.id.tv_tax_charges);
        tvCartEmptyHint = v.findViewById(R.id.tv_cart_empty_hint);
        llCart = v.findViewById(R.id.ll_cart);
    }

    public int getCartItemTotal() {
        return DatabaseHelper.getInstance(getContext()).getCartProductTotal();
    }

    @Override
    public void setQuantity(int itemId, int quantity) {
        DatabaseHelper.getInstance(getContext()).setQuantity(itemId, quantity);
    }

    @Override
    public void setTotal() {
        int total = getCartItemTotal();
        tvItemTotal.setText(String.valueOf(total));
//        tvGrandTotal.setText(String.valueOf((total * (12/100))));
        tvTaxesCharges.setText("12%");
    }

    @Override
    public void setCartEmptyHint() {
        llCart.setVisibility(View.GONE);
        tvCartEmptyHint.setVisibility(View.VISIBLE);
    }
}