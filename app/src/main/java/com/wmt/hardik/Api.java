package com.wmt.hardik;

import com.wmt.hardik.model.Register.Resgister;
import com.wmt.hardik.model.UserList.UserList;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class Api {

    public static ApiService postService=null;
    public static ApiService postService1=null;


    public static ApiService getPostService(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        if (postService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://68.183.48.101:3333/users/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            postService = retrofit.create(ApiService.class);
        }
        return postService;
    }

    public static ApiService getGetServise(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization","Bearer "+ "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjE1NjYsImlhdCI6MTYxOTMyNTAxNn0.koyUl-b7IkVnNur6Z7h0U6tABG_3x-MsOoQ71shkSn8").build();
                return chain.proceed(request);
            }
        });
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://68.183.48.101:3333/users/").client(httpClient.build()).build();

        postService1 = retrofit.create(Api.ApiService.class);

        return postService1;

    }



    public interface ApiService {

        @FormUrlEncoded
        @POST("register")
        Call<Resgister> getRegister(@Field("username") String username, @Field("email") String email, @Field("password") String password);


        @GET
        Call<UserList> getUsers(@Url String url);

    }


}