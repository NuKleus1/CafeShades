package com.example.cafeshades.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {

    @SerializedName("responseStatus")
    @Expose
    private String responseStatus;
    @SerializedName("orderList")
    @Expose
    private List<OrderModelClass> orderList = null;

    /**
     * No args constructor for use in serialization
     */
    public OrderResponse() {
    }

    /**
     * @param orderList
     * @param responseStatus
     */
    public OrderResponse(String responseStatus, List<OrderModelClass> orderList) {
        super();
        this.responseStatus = responseStatus;
        this.orderList = orderList;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<OrderModelClass> getOrderModelClassList() {
        return orderList;
    }

    public void setOrderModelClassList(List<OrderModelClass> orderList) {
        this.orderList = orderList;
    }
}
