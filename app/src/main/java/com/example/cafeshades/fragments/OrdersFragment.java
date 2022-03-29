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

import com.example.cafeshades.R;
import com.example.cafeshades.UserPreferences;
import com.example.cafeshades.adapters.OrderHistoryRVAdapter;
import com.example.cafeshades.models.OrderModelClass;
import com.example.cafeshades.models.OrderResponse;
import com.example.cafeshades.utils.APIClient;
import com.example.cafeshades.utils.UtilAPI;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {
    private final static String TAG = "OrdersFragment";
    RecyclerView rvOrderHistory;
    List<OrderModelClass> orderModelClassList;
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

        callUtilAPI();

    }

    public void callUtilAPI() {
        UtilAPI api = APIClient.getClient().create(UtilAPI.class);
        Log.d(TAG, UserPreferences.getPrefInstance(getContext()).getUserId());
        api.getUsersOrders("1").enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getResponseStatus().equals("true")) {
                        orderModelClassList = response.body().getOrderModelClassList();
                        OrderHistoryRVAdapter adapter = new OrderHistoryRVAdapter(getContext(), orderModelClassList);
                        rvOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvOrderHistory.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });
    }

    private void init() {
        rvOrderHistory = v.findViewById(R.id.rv_order);
    }
}
