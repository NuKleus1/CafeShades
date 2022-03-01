package com.example.cafeshades.fragments;

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
import com.example.cafeshades.adapters.RecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

public class FavouriteFragment extends Fragment {

    private static final String TAG = "FavouriteFragment";
    RecyclerViewAdapter adapter;
    DatabaseHelper db;
    private View v = null;
    RecyclerView recyclerView;
//    private ArrayList<ItemModelClass> dataList;

    public FavouriteFragment() {
        super(R.layout.fragment_favourite);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_favourite, container, false);
            Log.w(TAG, "onCreateViewNULL");
            db = DatabaseHelper.getInstance(getContext());
            init();
            setData();
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setData() {
//        dataList = db.getAllFavouriteItems();
        adapter = new RecyclerViewAdapter(db.getAllFavouriteItems(), getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void init() {
        recyclerView = v.findViewById(R.id.recycle_view_favourite);
    }
}