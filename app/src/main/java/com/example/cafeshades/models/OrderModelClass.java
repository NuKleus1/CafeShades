package com.example.cafeshades.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderModelClass {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("productList")
    @Expose
    private List<Product> productList = null;

    public OrderModelClass(String orderId, String orderDate, String orderStatus, String totalAmount, List<Product> productList) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.productList = productList;
    }


    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = OrderModelClass.this.productList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
