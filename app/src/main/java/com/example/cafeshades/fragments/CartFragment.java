package com.example.cafeshades.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.Helper.DatabaseHelper;
import com.example.cafeshades.R;
import com.example.cafeshades.adapters.RecycleViewAdapter;

public class CartFragment extends Fragment {
    private static final String TAG = "CART_FRAGMENT";
    View v = null;
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_cart, container, false);
            Log.w(TAG, "onCreateViewNULL");
            init();
            setData();
        }
        return v;
    }

    private void setData() {
        adapter = new RecycleViewAdapter(DatabaseHelper.getInstance(getContext()).getAllCartItems(), getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void init() {
        recyclerView = v.findViewById(R.id.recycle_view_cart);
    }

}