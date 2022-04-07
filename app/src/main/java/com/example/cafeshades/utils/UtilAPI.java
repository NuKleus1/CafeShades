package com.example.cafeshades.utils;

import com.example.cafeshades.models.MenuResponse;
import com.example.cafeshades.models.OrderResponse;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UtilAPI {
    @GET("getMenus.php")
    Call<MenuResponse> getMenu();

    @GET("getMyOrders.php")
    Call<OrderResponse> getUsersOrders(@Query("userId") String userId);

    @POST("submitOrder.php")
    Call<ResponseBody> submitOrder(@Field("userId") String userId, @Field("orderDetail") String orderDetail);
}
