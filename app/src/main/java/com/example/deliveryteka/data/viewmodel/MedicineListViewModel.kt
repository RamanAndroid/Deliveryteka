package com.example.deliveryteka.data.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.deliveryteka.data.models.Categories
import com.example.deliveryteka.data.models.Medicine
import com.example.deliveryteka.data.models.RequestAccess
import com.example.deliveryteka.data.models.User
import com.example.deliveryteka.data.repository.DeliverytekaRepository
import com.example.deliveryteka.utility.Constants
import kotlinx.coroutines.launch

class MedicineListViewModel @ViewModelInject constructor(
    private val repository: DeliverytekaRepository,
    application: Application,
) : AndroidViewModel(application) {

    var login: MutableLiveData<User> = MutableLiveData()
    var sighUp: MutableLiveData<User> = MutableLiveData()
    var addToBasket: MutableLiveData<Medicine> = MutableLiveData()
    var getBasket: MutableLiveData<Medicine> = MutableLiveData()
    var addToFavorite: MutableLiveData<Medicine> = MutableLiveData()
    var getFavorites: MutableLiveData<Medicine> = MutableLiveData()
    var getCategories: MutableLiveData<Categories> = MutableLiveData()

    private val currentQuery = MutableLiveData(Constants.DEFAULT_QUERY)
    val medicines = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchMedicines(query: String) {
        currentQuery.value = query
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

    fun addToFavorite(userId: Int, medicineId: String, count: Int) {
        viewModelScope.launch {
            val response = repository.addToFavorite(userId, medicineId, count)
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



}