package com.example.cafeshades.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("productId")
    @Expose
    private String productId = "";
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productImage")
    @Expose
    private String productImage;
    @SerializedName("productPrice")
    @Expose
    private String productPrice;

    private String productCategory;
    private String productCategoryId;

    @SerializedName("productQuantity")
    @Expose
    private int productQuantity;

    @SerializedName("total")
    @Expose
    private int total;

    private boolean productFavourite = false;

    public Product() {
    }

    public Product(String productId, String productName, String productImage, String productPrice) {
        super();
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public Product(String productCategory) {
        super();
        this.productCategory = productCategory;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public boolean isProductFavourite() {
        return productFavourite;
    }

    public void setProductFavourite(boolean itemIsFavourite) {
        this.productFavourite = itemIsFavourite;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductId(int productId) {
        this.productId = String.valueOf(productId);
    }

    public int getProductPrice() {
        return Integer.parseInt(productPrice);
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = String.valueOf(productPrice);
    }
}
