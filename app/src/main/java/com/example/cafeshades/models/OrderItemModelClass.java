package com.example.cafeshades.models;

public class OrderItemModelClass {
    private String quantity;
    private String itemName;
    private String itemPrice;

    public OrderItemModelClass(String quantity, String itemName, String itemPrice) {
        this.quantity = quantity;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public OrderItemModelClass(String quantity, String itemName) {
        this.quantity = quantity;
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
