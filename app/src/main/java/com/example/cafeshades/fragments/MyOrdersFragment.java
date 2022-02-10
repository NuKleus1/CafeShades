package com.example.cafeshades.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cafeshades.R;

import org.jetbrains.annotations.NotNull;

public class MyOrdersFragment extends Fragment {
    private View v = null;

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_my_orders, container, false);
        }
        return v;
    }
}
