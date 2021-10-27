package com.vik3.apiService;

import com.vik3.ui.models.ModelArtist;
import com.vik3.ui.models.ModelForgotPassword;
import com.vik3.ui.models.ModelForgotPasswordResponse;
import com.vik3.ui.models.ModelLogIResponse;
import com.vik3.ui.models.ModelLogInParameter;
import com.vik3.ui.models.ModelRegisterParameter;
import com.vik3.ui.models.ModelResponse;
import com.vik3.ui.models.ModelStations;
import com.vik3.ui.models.ModelSubscribeData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    //https://vik3.media/wp-json/getartist/v1/artist
    //http://vik3.eoxysitsolution.com/wp-json/getartist/v1/artist
//    @GET("getartist/v1/artist")
    @GET("wp-json/getartist/v1/artist")
    Call<List<ModelArtist>> getArtist();

    //http://vik3.eoxysitsolution.com/wp-json/api/artist
    //https://public.radio.co/stations/s24d6d2312/status
    @GET("stations/s24d6d2312/status")
    Call<ModelStations> getStations();

//https://vik3.media/wp-json/getdata/v1/insertdata
//    @FormUrlEncoded
    //http://vik3.eoxysitsolution.com/wp-json/getdata/v1/insertdata
    @POST("wp-json/getdata/v1/insertdata")
    Call<ModelResponse> subscribeData(@Body ModelSubscribeData subscribeData);
            /*@Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email);*/

    //http://vik3.eoxysitsolution.com/wp-json/driverapi/register
//    @FormUrlEncoded
    @POST("wp-json/driverapi/register")
    Call<ModelLogIResponse> register(@Body ModelRegisterParameter registerParameter);

    //http://vik3.eoxysitsolution.com/wp-json/driverapi/generate_auth_cookie
//    @FormUrlEncoded
    @POST("wp-json/driverapi/generate_auth_cookie")
    Call<ModelLogIResponse> login(@Body ModelLogInParameter logInParameter/*
            @Field("username") String username,
            @Field("password") String password*/);

    //http://vik3.eoxysitsolution.com/wp-json/driverapi/forgot_password
    @POST("wp-json/driverapi/forgot_password")
    Call<ModelForgotPasswordResponse> forgotPassword(@Body ModelForgotPassword forgotPassword);

}
