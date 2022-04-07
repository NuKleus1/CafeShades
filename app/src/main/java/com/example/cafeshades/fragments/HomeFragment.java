package com.example.cafeshades.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.Helper.DatabaseHelper;
import com.example.cafeshades.R;
import com.example.cafeshades.adapters.HomeFavouriteRVAdapter;
import com.example.cafeshades.models.Category;
import com.example.cafeshades.models.MenuResponse;
import com.example.cafeshades.models.Product;
import com.example.cafeshades.utils.APIClient;
import com.example.cafeshades.utils.UtilAPI;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";
    ArrayList<Product> productArrayList = new ArrayList<>();
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    ArrayList<Integer> categoryChipIds = new ArrayList<>();
    Dictionary<Integer, Integer> chipIdPosition = new Hashtable<>();
    private boolean flag = false;
    private HomeFavouriteRVAdapter adapter;
    private HorizontalScrollView horizontalScrollView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recyclerView;
    private RecyclerView.SmoothScroller smoothScroller;
    private LinearLayoutManager rvLinearLayoutManager;
    private ChipGroup chipGroup;
    private DatabaseHelper db;
    private View v = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_home, container, false);
            Log.w(TAG, "onCreateViewNULL");
            init();
            setData();
            setListeners();
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void setData() {
        shimmerFrameLayout.startShimmer();
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
                        setUpData();
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

    private void setUpData() {
        List<String> productIds = new ArrayList<>();
        Integer pos = 0;
        for (Category category : categoryArrayList) {

            String categoryName = category.getCategoryName();
            String categoryId = category.getCategoryId();

            // Add Category id and name into productArraylist to show different view within single RV.
            productArrayList.add(new Product(categoryName));

            //Add Category Filter Chip with Name and generated ID
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.filter_chips_layout, chipGroup, false);
            int chipId = View.generateViewId();
            chip.setId(chipId);
            chip.setText(category.getCategoryName());
            chipGroup.addView(chip);

            // Insert chip id and the position into Dictionary.
            chipIdPosition.put(chipId, pos);
            pos = pos + 1;

            // Add all the products within current category into productArrayList
            for (Product product : category.getProductList()) {
                product.setProductFavourite(db.getFavourite(product.getProductId()));
                product.setProductQuantity(db.getQuantity(product.getProductId()));
                productIds.add(product.getProductId());
                product.setProductCategory(categoryName);
                product.setProductCategoryId(categoryId);
                productArrayList.add(product);
                pos = pos + 1;

                db.insertProduct(product);
            }
        }

        db.updateProductAndCartTable(productIds);

        setUpProductListUI();
    }

    private void setUpProductListUI() {
        adapter = new HomeFavouriteRVAdapter(productArrayList, getContext());
        rvLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rvLinearLayoutManager);
        recyclerView.setAdapter(adapter);

        smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        new Handler().postDelayed(new Runnable() {
            public void run() {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                horizontalScrollView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }, 900);
    }

    private void init() {
        db = DatabaseHelper.getInstance(getContext());
        horizontalScrollView = v.findViewById(R.id.horizontal_view_chips);
        shimmerFrameLayout = v.findViewById(R.id.shimmer_frame_layout);
        recyclerView = v.findViewById(R.id.recycle_view_home);
        chipGroup = v.findViewById(R.id.chip_group_filter_categories);
    }

    private void setListeners() {
        chipGroup.setSingleSelection(true);
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == -1){
                    return;
                }
                if (checkedId == R.id.chip_add_all) {
                    smoothScroller.setTargetPosition(0);
                } else {
                    smoothScroller.setTargetPosition(chipIdPosition.get(checkedId));
                }
                rvLinearLayoutManager.startSmoothScroll(smoothScroller);
            }
        });
    }
}