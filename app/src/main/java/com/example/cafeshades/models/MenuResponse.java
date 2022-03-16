package com.example.cafeshades.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MenuResponse {

    @SerializedName("responseStatus")
    @Expose
    private String responseStatus;
    @SerializedName("categoryList")
    @Expose
    private List<Category> categoryList = null;

    /**
     * No args constructor for use in serialization
     */
    public MenuResponse() {
    }

    /**
     * @param categoryList
     * @param responseStatus
     */
    public MenuResponse(String responseStatus, List<Category> categoryList) {
        super();
        this.responseStatus = responseStatus;
        this.categoryList = categoryList;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}

