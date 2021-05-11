package com.example.deliveryteka.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.deliveryteka.api.DeliverytekaApi
import com.example.deliveryteka.data.MedicinePagingSource
import com.example.deliveryteka.data.models.Categories
import com.example.deliveryteka.data.models.Medicine
import com.example.deliveryteka.data.models.RequestAccess
import com.example.deliveryteka.data.models.User
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

    suspend fun login(requestAccess: RequestAccess): User {
        return deliverytekaApi.login(requestAccess)
    }

    suspend fun sighUp(requestAccess: RequestAccess): User {
        return deliverytekaApi.signUp(requestAccess)
    }

    suspend fun addToBasket(userId:Int,medicineId:String,count:Int): Medicine {
        return deliverytekaApi.addToBasket(userId, medicineId, count)
    }

    suspend fun getBasket(userId: Int): Medicine {
        return deliverytekaApi.getBasket(userId)
    }

    suspend fun addToFavorite(userId:Int,medicineId:String,count:Int): Medicine {
        return deliverytekaApi.addToFavorite(userId, medicineId, count)
    }

    suspend fun getFavorites(userId: Int): Medicine {
        return deliverytekaApi.getFavorites(userId)
    }

    suspend fun getCategories(): Categories {
        return deliverytekaApi.getCategories()
    }


}