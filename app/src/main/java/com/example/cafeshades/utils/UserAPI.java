package com.example.cafeshades.utils;

import com.example.cafeshades.models.APIResponse;
import com.example.cafeshades.models.UserData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserAPI {

    @FormUrlEncoded
    @POST("userLogin.php")
    Call<UserData> getUser(@Field("mobileNumber") String mobileNumber, @Field("userRole") String role);

    @FormUrlEncoded
    @POST("userRegistration.php")
    Call<UserData> userRegistration(@Field("name") String name,
                                        @Field("buildingName") String buildingName,
                                        @Field("floorNumber") String floorNumber,
                                        @Field("officeNumber") String officeNumber,
                                        @Field("landmark") String landmark,
                                        @Field("mobileNumber") String mobileNumber,
                                        @Field("fcmToken") String fcmToken,
                                        @Field("userRole") String userRole);

    @FormUrlEncoded
    @POST("updateUserProfile.php")
    Call<APIResponse> updateUserProfile(@Field("name") String name,
                                        @Field("buildingName") String buildingName,
                                        @Field("floorNumber") String floorNumber,
                                        @Field("officeNumber") String officeNumber,
                                        @Field("landmark") String landmark,
                                        @Field("userId") String userId);
}
