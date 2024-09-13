package com.example.android.chefapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.android.chefapp.database.getDatabase
import com.example.android.chefapp.domain.SummaryItems
import com.example.android.chefapp.domain.SummaryOrderState
import com.example.android.chefapp.domain.SummaryOrderType
import com.example.android.chefapp.repository.SummaryRepository
import kotlinx.coroutines.launch

class SummaryViewModel(application: Application, val terminal: Int?, val branch: Int?) :
    AndroidViewModel(application) {

    val database = getDatabase(application)
    val repo = SummaryRepository(database)

    private val _orderTypes = MutableLiveData<List<SummaryOrderType>>()
    val orderType: LiveData<List<SummaryOrderType>>
        get() = _orderTypes

    private val _orderStates = MutableLiveData<List<SummaryOrderState>>()
    val orderStates: LiveData<List<SummaryOrderState>>
        get() = _orderStates

    private val _items = MutableLiveData<List<SummaryItems>>()
    val items: LiveData<List<SummaryItems>>
        get() = _items

    init {
        getOrderTypes()
        getOrderStates()
        getItems()
    }

    private fun getOrderTypes() {
        viewModelScope.launch {
            getOrderTypeModels()
        }
    }

    private suspend fun getOrderTypeModels() {
        _orderTypes.value = repo.getOrderType(terminal, branch)
    }

    private fun getOrderStates() {
        viewModelScope.launch {
            getOrderStateModels()
        }
    }

    private suspend fun getOrderStateModels() {
        _orderStates.value = repo.getOrderState(terminal, branch)
    }

    private fun getItems() {
        viewModelScope.launch {
            getItemModels()
        }
    }

    private suspend fun getItemModels() {
        _items.value = repo.getSummaryItems(terminal, branch)
    }

    class Factory(val app: Application, val terminal: Int?, val branch: Int?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SummaryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SummaryViewModel(app, terminal, branch) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}