package com.example.deliveryteka.api

import com.example.deliveryteka.data.models.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DeliverytekaApi {

    @GET("get_medicine.php")
    suspend fun searchMedicine(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Medicine

    @GET("get_medicine_by_category.php")
    suspend fun searchMedicineCategory(
        @Query("user_id") userId: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Medicine

    @POST("login.php")
    suspend fun login(@Body requestAccess: RequestAccess): UserShortInfo

    @POST("signup.php")
    suspend fun signUp(@Body requestAccess: RequestAccess): UserShortInfo

    @GET("remove_basket.php")
    suspend fun removeBasket(
        @Query("user_id") userId: Int,
        @Query("medicine_id") medicineId: String
    ): BasketMedicine

    @GET("add_to_basket.php")
    suspend fun addToBasket(
        @Query("user_id") userId: Int,
        @Query("medicine_id") medicineId: String,
        @Query("count") count: Int
    ): Medicine

    @GET("get_basket.php")
    suspend fun getBasket(
        @Query("user_id") userId: Int
    ): BasketMedicine

    @GET("remove_favorit.php")
    suspend fun removeFavorite(
        @Query("user_id") userId: Int,
        @Query("medicine_id") medicineId: String
    ): Medicine

    @GET("add_to_favorites.php")
    suspend fun addToFavorite(
        @Query("user_id") userId: Int,
        @Query("medicine_id") medicineId: String
    ): Medicine

    @GET("get_favorites.php")
    suspend fun getFavorites(
        @Query("user_id") userId: Int
    ): Medicine

    @GET("get_categories.php")
    suspend fun getCategories(): Categories

    @GET("get_user.php")
    suspend fun getUserInfo(
        @Query("user_id") userId: Int
    ): UserInfo

    @GET("update_user_info.php")
    suspend fun updateUserInfo(
        @Query("user_id") userId: Int,
        @Query("user_name") userName: String,
        @Query("user_address") userAddress: String
    ): UserInfo

    @GET("update_user_password.php")
    suspend fun updateUserPassword(
        @Query("user_id") userId: Int,
        @Query("user_password") userPassword: String,
        @Query("new_password") newPassword: String
    ):UserShortInfo

    @GET("update_med_card.php")
    suspend fun updateUserMedCard(
        @Query("user_id") userId: Int,
        @Query("med_card_number") medCardNumber: String
    )

    @GET("covid_info.php")
    suspend fun covidInfo(): CovidInfo
}