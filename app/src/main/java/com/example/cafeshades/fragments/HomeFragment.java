package com.example.cafeshades.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.Helper.DatabaseHelper;
import com.example.cafeshades.R;
import com.example.cafeshades.UserPreferences;
import com.example.cafeshades.adapters.RecyclerViewAdapter;
import com.example.cafeshades.models.ItemModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<ItemModelClass> itemDataList = new ArrayList<>();
    String TAG = "HomeFragment";
    View v = null;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_home, container, false);
            Log.w(TAG, "onCreateViewNULL");
            init();
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w(TAG, "onViewCreated");
        setData();

    }

    private void setData() {

        if (UserPreferences.getPrefInstance().getDatabaseCreated()) {
            itemDataList = DatabaseHelper.getInstance(getContext()).getAllItems();
        } else {
            setItemData();
        }

        adapter = new RecyclerViewAdapter(itemDataList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setItemData() {
        itemDataList.add(new ItemModelClass("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 321, 256));
        itemDataList.add(new ItemModelClass("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 322, 635));
        itemDataList.add(new ItemModelClass("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 323, 54));
        itemDataList.add(new ItemModelClass("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 324, 250));
        itemDataList.add(new ItemModelClass("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 325, 520));
        itemDataList.add(new ItemModelClass("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 326, 333));
        itemDataList.add(new ItemModelClass("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 327, 45));


        for (ItemModelClass item : itemDataList) {
            DatabaseHelper.getInstance(getContext()).insertItems(item);
        }
        Log.w(TAG, "Static Data Added to the Database");

        UserPreferences.getPrefInstance().setDatabaseCreated(true);
    }


    private Bitmap getBitmap() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.cappuccino_coffee);
    }

    private void init() {
        recyclerView = v.findViewById(R.id.recycle_view_home);
    }
}