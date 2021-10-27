package com.vik3.apiClient;

import androidx.annotation.NonNull;

import com.vik3.BuildConfig;
import com.vik3.apiService.ApiService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vik3.BuildConfig.BASE_URL;

public class RetrofitClient {

    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        //.addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMzQuMjA5LjE0NC40OVwvYXBpXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYwMjA3MDE2OCwiZXhwIjoxNjAyMDczNzY4LCJuYmYiOjE2MDIwNzAxNjgsImp0aSI6ImY3WUxEQnhvallpcmk4NmIiLCJzdWIiOjIsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.zH1vmzbq_BDSwgv3VJjiZRCRxg3i0Y1Fe-w2__ufhDk")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        /*OkHttpClient httpClient = new OkHttpClient();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application/json");
                return chain.proceed(requestBuilder.build());
            }
        });*/
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    @NonNull
    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
