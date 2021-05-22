package com.example.deliveryteka.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.deliveryteka.api.DeliverytekaApi
import com.example.deliveryteka.data.CatalogRecipePagingSource
import com.example.deliveryteka.data.MedicinePagingSource
import com.example.deliveryteka.data.models.*
import com.example.deliveryteka.utility.Constants
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DeliverytekaRepository @Inject constructor(private val deliverytekaApi: DeliverytekaApi) {


    fun getSearchResults(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MedicinePagingSource(deliverytekaApi, query) }
    ).liveData

    fun getSearchCategoryResults(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CatalogRecipePagingSource(deliverytekaApi, query, Constants.ID) }
    ).liveData


    suspend fun login(requestAccess: RequestAccess): UserShortInfo {
        return deliverytekaApi.login(requestAccess)
    }

    suspend fun sighUp(requestAccess: RequestAccess): UserShortInfo {
        return deliverytekaApi.signUp(requestAccess)
    }

    suspend fun removeBasket(userId: Int, medicineId: String): BasketMedicine {
        return deliverytekaApi.removeBasket(userId, medicineId)
    }

    suspend fun addToBasket(userId: Int, medicineId: String, count: Int): Medicine {
        return deliverytekaApi.addToBasket(userId, medicineId, count)
    }

    suspend fun getBasket(userId: Int): BasketMedicine {
        return deliverytekaApi.getBasket(userId)
    }

    suspend fun removeFavorite(userId: Int, medicineId: String): Medicine {
        return deliverytekaApi.removeFavorite(userId, medicineId)
    }

    suspend fun addToFavorite(userId: Int, medicineId: String): Medicine {
        return deliverytekaApi.addToFavorite(userId, medicineId)
    }

    suspend fun getFavorites(userId: Int): Medicine {
        return deliverytekaApi.getFavorites(userId)
    }

    suspend fun getCategories(): Categories {
        return deliverytekaApi.getCategories()
    }

    suspend fun getUserInfo(userId: Int): UserInfo {
        return deliverytekaApi.getUserInfo(userId)
    }

    suspend fun updateUserInfo(userId: Int, userName: String, userAddress: String): UserInfo {
        return deliverytekaApi.updateUserInfo(userId, userName, userAddress)
    }

    suspend fun updateUserPassword(
        userId: Int,
        userPassword: String,
        newPassword: String
    ): UserShortInfo {
        return deliverytekaApi.updateUserPassword(userId, userPassword, newPassword)
    }

    suspend fun updateUserMedCard(userId: Int, medCardNumber: String) {
        return deliverytekaApi.updateUserMedCard(userId, medCardNumber)
    }

    suspend fun covidInfo(): CovidInfo {
        return deliverytekaApi.covidInfo()
    }

}