package com.example.cafeshades.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIResponse {

    @SerializedName("responseStatus")
    @Expose
    private String responseStatus;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    /**
     * No args constructor for use in serialization
     */
    public APIResponse() {
    }

    /**
     * @param responseStatus
     * @param responseMessage
     */
    public APIResponse(String responseStatus, String responseMessage) {
        super();
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
