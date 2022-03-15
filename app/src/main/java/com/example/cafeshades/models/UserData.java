package com.example.cafeshades.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("responseStatus")
    @Expose
    private String responseStatus;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     */
    public UserData() {
    }

    /**
     * @param responseStatus
     * @param responseMessage
     * @param user
     */
    public UserData(String responseStatus, String responseMessage, User user) {
        super();
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public class User {

        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("buildingName")
        @Expose
        private String buildingName;
        @SerializedName("floorNumber")
        @Expose
        private String floorNumber;
        @SerializedName("officeNumber")
        @Expose
        private String officeNumber;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("mobileNumber")
        @Expose
        private String mobileNumber;

        /**
         * No args constructor for use in serialization
         */
        public User() {
        }

        /**
         * @param buildingName
         * @param mobileNumber
         * @param officeNumber
         * @param name
         * @param floorNumber
         * @param landmark
         * @param userId
         */
        public User(String userId, String name, String buildingName, String floorNumber, String officeNumber, String landmark, String mobileNumber) {
            super();
            this.userId = userId;
            this.name = name;
            this.buildingName = buildingName;
            this.floorNumber = floorNumber;
            this.officeNumber = officeNumber;
            this.landmark = landmark;
            this.mobileNumber = mobileNumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getFloorNumber() {
            return floorNumber;
        }

        public void setFloorNumber(String floorNumber) {
            this.floorNumber = floorNumber;
        }

        public String getOfficeNumber() {
            return officeNumber;
        }

        public void setOfficeNumber(String officeNumber) {
            this.officeNumber = officeNumber;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

    }


}
