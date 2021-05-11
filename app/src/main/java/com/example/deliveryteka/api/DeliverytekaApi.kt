package com.example.deliveryteka.api

import com.example.deliveryteka.data.models.Categories
import com.example.deliveryteka.data.models.Medicine
import com.example.deliveryteka.data.models.RequestAccess
import com.example.deliveryteka.data.models.User
import retrofit2.http.*

interface DeliverytekaApi {

    @GET("get_medicine.php")
    suspend fun searchMedicine(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Medicine

    @POST("login.php")
    suspend fun login(@Body requestAccess: RequestAccess): User

    @POST("signup.php")
    suspend fun signUp(@Body requestAccess: RequestAccess): User

    @GET("add_to_basket.php")
    suspend fun addToBasket(
        @Query("user_id") userId: Int,
        @Query("medicine_id") medicineId: String,
        @Query("count") count: Int
    ): Medicine

    @GET("get_basket.php")
    suspend fun getBasket(
        @Query("user_id") userId: Int
    ): Medicine

    @GET("add_to_favorites.php")
    suspend fun addToFavorite(
        @Query("user_id") userId: Int,
        @Query("medicine_id") medicineId: String,
        @Query("count") count: Int
    ): Medicine

    @GET("get_favorites.php")
    suspend fun getFavorites(
        @Query("user_id") userId: Int
    ): Medicine

    @GET("get_categories.php")
    suspend fun getCategories(): Categories

}