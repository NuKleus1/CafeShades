package com.example.cafeshades.models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ItemModelClass {

    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private Bitmap itemImage;
    private int itemID;
    private int itemPrice;
    private int itemQuantity = 0;
    private boolean itemFavourite = false;

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }


    public ItemModelClass() {
    }

    public ItemModelClass(String itemName, String itemDescription, String itemCategory, Bitmap itemImage, int itemID, int itemPrice) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemImage = itemImage;
        this.itemID = itemID;
        this.itemPrice = itemPrice;
    }

    public ItemModelClass(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
//        this.itemImage = itemImage;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Bitmap getItemImage() {
        return itemImage;
    }

    public void setItemImage(Bitmap itemImage) {
        this.itemImage = itemImage;
    }

    public boolean isItemFavourite() {
        return itemFavourite;
    }

    public void setItemFavourite(boolean itemIsFavourite) {
        this.itemFavourite = itemIsFavourite;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }
}
