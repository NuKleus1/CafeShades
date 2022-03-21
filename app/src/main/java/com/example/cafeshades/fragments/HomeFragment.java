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
import com.example.cafeshades.UserPreferences;
import com.example.cafeshades.adapters.RecyclerViewAdapter;
import com.example.cafeshades.models.Category;
import com.example.cafeshades.models.MenuResponse;
import com.example.cafeshades.models.Product;
import com.example.cafeshades.utils.APIClient;
import com.example.cafeshades.utils.UtilAPI;

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
    private View v = null;
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
//            setData();
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
        Log.w(TAG, "onViewCreated");

    }

    private void setData() {

//        if (UserPreferences.getPrefInstance().getDatabaseCreated()) {
//            productArrayList = DatabaseHelper.getInstance(getContext()).getAllProducts();
//        } else {
//            setItemData();
//        }
        adapter = new RecyclerViewAdapter(productArrayList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        callUtilAPI();

//        Log.d(TAG, String.valueOf(DatabaseHelper.getInstance(getContext()).getAllProducts().isEmpty()));
//        for (Product product :
//                DatabaseHelper.getInstance(getContext()).getAllProducts()) {
//            Log.d(TAG, product.toString());
//        }

//        Log.d(TAG, String.valueOf(productArrayList.get(1).getProductName()));
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
                            for (Product product : category.getProductList()) {
                                productIds.add(product.getProductId());
                                product.setProductCategory(categoryName);
                                product.setProductCategoryId(categoryId);
                                productArrayList.add(product);
                            }
                        }
//                        DatabaseHelper.getInstance(getContext()).updateProductAndCartTable(productIds);
                        for (Product product : productArrayList) {
                            DatabaseHelper.getInstance(getContext()).insertProduct(product);
                        }
//                        productArrayList = DatabaseHelper.getInstance(getContext()).getAllProducts();
                        adapter.setData(productArrayList);
                        Log.d(TAG, "/adapter" + productArrayList.isEmpty() );
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

    private void setItemData() {
//        itemDataList.add(new Product("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 321, 256));
//        itemDataList.add(new Product("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 322, 635));
//        itemDataList.add(new Product("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 323, 54));
//        itemDataList.add(new Product("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 324, 250));
//        itemDataList.add(new Product("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 325, 520));
//        itemDataList.add(new Product("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 326, 333));
//        itemDataList.add(new Product("Black Forest Cup", "nice coffee", "Coffee", getBitmap(), 327, 45));

        Log.w(TAG, "Static Data Added to the Database");

        UserPreferences.getPrefInstance(getContext()).setDatabaseCreated(true);
    }

    private void init() {
        recyclerView = v.findViewById(R.id.recycle_view_home);
    }
}