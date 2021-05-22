package com.example.deliveryteka.data.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.deliveryteka.data.models.*
import com.example.deliveryteka.data.repository.DeliverytekaRepository
import com.example.deliveryteka.utility.Constants
import kotlinx.coroutines.launch

class DeliverytekaViewModel @ViewModelInject constructor(
    private val repository: DeliverytekaRepository,
    application: Application,
) : AndroidViewModel(application) {

    var login: MutableLiveData<UserShortInfo> = MutableLiveData()
    var sighUp: MutableLiveData<UserShortInfo> = MutableLiveData()
    var addToBasket: MutableLiveData<Medicine> = MutableLiveData()
    var getBasket: MutableLiveData<BasketMedicine> = MutableLiveData()
    var addToFavorite: MutableLiveData<Medicine> = MutableLiveData()
    var getFavorites: MutableLiveData<Medicine> = MutableLiveData()
    var getCategories: MutableLiveData<Categories> = MutableLiveData()
    var getUserInfo: MutableLiveData<UserInfo> = MutableLiveData()
    var covidInfo: MutableLiveData<CovidInfo> = MutableLiveData()

    private val currentQueryMedicine = MutableLiveData(Constants.DEFAULT_QUERY)
    val medicines = currentQueryMedicine.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchMedicines(query: String) {
        currentQueryMedicine.value = query
    }

    private val currentQueryCategory = MutableLiveData(Constants.TITLE)
    val category = currentQueryCategory.switchMap { queryString ->
        repository.getSearchCategoryResults(queryString).cachedIn(viewModelScope)
    }


    fun login(requestAccess: RequestAccess) {
        viewModelScope.launch {
            val response = repository.login(requestAccess)
            login.value = response
        }
    }


    fun sighUp(requestAccess: RequestAccess) {
        viewModelScope.launch {
            val response = repository.sighUp(requestAccess)
            sighUp.value = response
        }
    }

    fun removeBasket(userId: Int, medicineId: String) {
        viewModelScope.launch {
            val response = repository.removeBasket(userId, medicineId)
            getBasket.value = response
        }
    }

    fun addToBasket(userId: Int, medicineId: String, count: Int) {
        viewModelScope.launch {
            val response = repository.addToBasket(userId, medicineId, count)
            addToBasket.value = response
        }
    }

    fun getBasket(userId: Int) {
        viewModelScope.launch {
            val response = repository.getBasket(userId)
            getBasket.value = response
        }
    }

    fun removeFavorite(userId: Int, medicineId: String) {
        viewModelScope.launch {
            val response = repository.removeFavorite(userId, medicineId)
            getFavorites.value = response
        }
    }

    fun addToFavorite(userId: Int, medicineId: String) {
        viewModelScope.launch {
            val response = repository.addToFavorite(userId, medicineId)
            addToFavorite.value = response
        }
    }

    fun getFavorites(userId: Int) {
        viewModelScope.launch {
            val response = repository.getFavorites(userId)
            getFavorites.value = response
        }
    }


    fun getCategories() {
        viewModelScope.launch {
            val response = repository.getCategories()
            getCategories.value = response
        }
    }

    fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            val response = repository.getUserInfo(userId)
            getUserInfo.value = response
        }
    }

    fun updateUserInfo(userId: Int, userName: String, userAddress: String) {
        viewModelScope.launch {
            repository.updateUserInfo(userId, userName, userAddress)
        }
    }

    fun updateUserPassword(userId: Int, userPassword: String,newPassword: String) {
        viewModelScope.launch {
            val response = repository.updateUserPassword(userId,userPassword, newPassword)
            login.value= response
        }
    }

    fun updateUserMedCard(userId: Int, medCardNumber: String) {
        viewModelScope.launch {
            repository.updateUserMedCard(userId, medCardNumber)
        }
    }

    fun covidInfo() {
        viewModelScope.launch {
            val response = repository.covidInfo()
            covidInfo.value = response
        }
    }

}