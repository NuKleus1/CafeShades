package com.example.cafeshades.models;

import java.util.ArrayList;

public class OrderModelClass {
    private String orderNumber;
    private String date;
    private String orderStatus;
    private String totalAmount;
    private ArrayList<OrderItemModelClass> orderItemModelClassArrayList;

    public OrderModelClass(String orderNumber, String date, String orderStatus, String totalAmount, ArrayList<OrderItemModelClass> orderItemModelClassArrayList) {
        this.orderNumber = orderNumber;
        this.date = date;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.orderItemModelClassArrayList = orderItemModelClassArrayList;
    }

    public ArrayList<OrderItemModelClass> getOrderItemModelClassArrayList() {
        return orderItemModelClassArrayList;
    }

    public void setOrderItemModelClassArrayList(ArrayList<OrderItemModelClass> orderItemModelClassArrayList) {
        this.orderItemModelClassArrayList = orderItemModelClassArrayList;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
