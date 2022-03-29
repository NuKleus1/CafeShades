package com.example.cafeshades.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.Helper.DatabaseHelper;
import com.example.cafeshades.R;
import com.example.cafeshades.adapters.HomeFavouriteRVAdapter;
import com.example.cafeshades.models.Category;
import com.example.cafeshades.models.MenuResponse;
import com.example.cafeshades.models.Product;
import com.example.cafeshades.utils.APIClient;
import com.example.cafeshades.utils.UtilAPI;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    ArrayList<Product> productArrayList = new ArrayList<>();
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    String TAG = "HomeFragment";
    boolean flag = false;
    HomeFavouriteRVAdapter adapter;
    RecyclerView recyclerView;
    ChipGroup chipGroup;
    private DatabaseHelper db;
    private View v = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_home, container, false);
            Log.w(TAG, "onCreateViewNULL");
            init();
            setData();
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w(TAG, "onViewCreated");
        if (flag) {
            for (Product product : productArrayList) {
                product.setProductFavourite(db.getFavourite(product.getProductId()));
                product.setProductQuantity(db.getQuantity(product.getProductId()));
            }
            adapter.notifyDataSetChanged();
        } else {
            flag = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
//        if (flag) {
////            for (Product product : productArrayList) {
////                product.setProductFavourite(db.getFavourite(product.getProductId()));
////                product.setProductQuantity(db.getQuantity(product.getProductId()));
////            }
//            productArrayList = db.getAllProducts();
//            adapter.setData(productArrayList);
//        } else {
//            flag = true;
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void setData() {
        callUtilAPI();
    }

    private void callUtilAPI() {
        UtilAPI api = APIClient.getClient().create(UtilAPI.class);
        api.getMenu().enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getResponseStatus().equals("true")) {
                        Log.d(TAG, "/MenuResponseTrue: ");
                        categoryArrayList = (ArrayList<Category>) response.body().getCategoryList();
                        List<String> productIds = new ArrayList<>();
                        for (Category category : categoryArrayList) {
                            String categoryName = category.getCategoryName();
                            String categoryId = category.getCategoryId();
                            productArrayList.add(new Product(categoryName));
                            for (Product product : category.getProductList()) {
                                product.setProductFavourite(db.getFavourite(product.getProductId()));
                                product.setProductQuantity(db.getQuantity(product.getProductId()));
                                productIds.add(product.getProductId());
                                product.setProductCategory(categoryName);
                                product.setProductCategoryId(categoryId);
                                productArrayList.add(product);
                            }
                        }
                        db.updateProductAndCartTable(productIds);
                        for (Product product : productArrayList) {
                            db.insertProduct(product);
                        }
                        setCategoryChips();
//                        productArrayList = db.getAllProducts();
                        adapter = new HomeFavouriteRVAdapter(productArrayList, getContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d(TAG, "/MenuResponseFalse");
                    }
                } else {
                    Log.d(TAG, "/MenuResponse Code: " + response.code());
                    Toast.makeText(getContext(), "Error Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                Log.d(TAG, "/MenuResponseFailure");
                t.printStackTrace();
            }
        });
    }

    public void setCategoryChips() {
        View view;
        Chip chip;
        for (Category category :
                categoryArrayList) {
            view = getLayoutInflater().inflate(R.layout.filter_chips_layout, null);
            chip = view.findViewById(R.id.filter_chip);
            chip.setText(category.getCategoryName());
            chipGroup.addView(chip);
        }
    }

    private void init() {
        db = DatabaseHelper.getInstance(getContext());
        recyclerView = v.findViewById(R.id.recycle_view_home);
        chipGroup = v.findViewById(R.id.chip_group_filter_categories);
    }
}