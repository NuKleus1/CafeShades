package com.example.cafeshades.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.R;
import com.example.cafeshades.adapters.OrderHistoryRVAdapter;
import com.example.cafeshades.models.OrderItemModelClass;
import com.example.cafeshades.models.OrderModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {
    RecyclerView rvOrderHistory;
    ArrayList<OrderModelClass> orderList = new ArrayList<>();
    private View v = null;

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_orders, container, false);
            init();
            setData();
        }
        return v;
    }

    private void setData() {
        ArrayList<OrderItemModelClass> item = new ArrayList<>();

        item.add(new OrderItemModelClass("2", "Cappuccino", "453"));
        item.add(new OrderItemModelClass("2", "Cappuccino", "453"));
        item.add(new OrderItemModelClass("2", "Cappuccino", "453"));
        item.add(new OrderItemModelClass("2", "Cappuccino", "453"));

        orderList.add(new OrderModelClass("5432", "24 Sep 2022", "Confirmed", "245", item));
        orderList.add(new OrderModelClass("5432", "24 Sep 2022", "Confirmed", "245", item));
        orderList.add(new OrderModelClass("5432", "24 Sep 2022", "Confirmed", "245", item));
        orderList.add(new OrderModelClass("5432", "24 Sep 2022", "Confirmed", "245", item));
        orderList.add(new OrderModelClass("5432", "24 Sep 2022", "Confirmed", "245", item));

        OrderHistoryRVAdapter adapter = new OrderHistoryRVAdapter(getContext(), orderList);
        rvOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOrderHistory.setAdapter(adapter);
    }

    private void init() {
        rvOrderHistory = v.findViewById(R.id.rv_order);
    }
}
