package com.example.mvpgen2.WebService

import com.example.mvpgen2.Model.Auth
import com.example.mvpgen2.Model.Barang
import com.example.mvpgen2.Response.ListResponse
import com.example.mvpgen2.Response.SingleResponse
import retrofit2.Call
import retrofit2.http.*

interface APIClient {

    //RetrofitLogin
    @FormUrlEncoded
    @POST("auth/sign-up")
    fun SignUp(@Field("name") name: String,
               @Field("username") username: String,
               @Field("email") email: String,
               @Field("password") password: String
    ): Call<SingleResponse<Auth>>

    @FormUrlEncoded
    @POST("auth/sign-in")
    fun SignIn(@Field("username") username: String,
               @Field("password") password: String): Call<SingleResponse<Auth>>

    // Retrofit CRUD
    @GET("barang")
    fun getData(): Call<ListResponse<Barang>>

    @GET("barang/{id}")
    fun getbyId(@Path("id") id: Int): Call<ListResponse<Barang>>

    @DELETE("barang/{id}")
    fun deletebyId(@Path("id") id: Int): Call<SingleResponse<Barang>>

    @FormUrlEncoded
    @POST("barang/")
    fun addItem(
            @Field("nama") nama: String,
            @Field("kode") kode: Int,
    ): Call<SingleResponse<Barang>>

    @FormUrlEncoded
    @PUT("barang/{id}")
    fun updateItem(@Path("id") id: Int,
                   @Field("nama") nama: String,
                   @Field("kode") kode: Int
    ): Call<SingleResponse<Barang>>

}