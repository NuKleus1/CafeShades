package com.example.cafeshades.utils;

import com.example.cafeshades.models.MenuResponse;
import com.example.cafeshades.models.OrderResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UtilAPI {
    @GET("getMenus.php")
    Call<MenuResponse> getMenu();

    @GET("getMyOrders.php")
    Call<OrderResponse> getUsersOrders(@Query("userId") String userId);
}
