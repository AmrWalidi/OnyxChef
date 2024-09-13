package com.example.android.chefapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.android.chefapp.database.getDatabase
import com.example.android.chefapp.domain.Order
import com.example.android.chefapp.repository.OrdersRepository
import kotlinx.coroutines.launch

class OrdersViewModel(app: Application, private val branch: Int?, private val terminal: Int?) :
    AndroidViewModel(app) {

    val database = getDatabase(app)
    private val repo = OrdersRepository(database)

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    private val _ordersNumber = MutableLiveData(0)
    val ordersNumber: LiveData<Int>
        get() = _ordersNumber

    private val _ordersNext = MutableLiveData(0)
    val ordersNext: LiveData<Int>
        get() = _ordersNext

    private val _ordersPrev = MutableLiveData(0)
    val ordersPrev: LiveData<Int>
        get() = _ordersPrev

    private val _totalPage = MutableLiveData(1)
    val totalPage: LiveData<Int>
        get() = _totalPage

    private val _currentPage = MutableLiveData(1)
    val currentPage: LiveData<Int>
        get() = _currentPage


    fun getOrders() {
        viewModelScope.launch {
            getSavedOrders()
        }
    }

    private suspend fun getSavedOrders() {
        _orders.value = _currentPage.value?.let { repo.getOrders(it - 1) }
        if (_orders.value?.isEmpty() == true) {
            refresh()
        } else {
            getOrdersNumbers()
        }
    }

    private suspend fun getOrdersNumbers() {
        _ordersNumber.value = repo.getOrdersNumber()
        if (_ordersNumber.value == 0) {
            _ordersNext.value = 0
            _totalPage.value = 1
        } else {
            if (_ordersNumber.value!!.mod(4) == 0) {
                _totalPage.value = _ordersNumber.value!!.div(4)
                _ordersNext.value = _ordersNumber.value!! - 4
            } else {
                _totalPage.value = _ordersNumber.value!!.div(4) + 1
            }
            _ordersNext.value = _ordersNumber.value!! - 4
        }
        _currentPage.value = 1
        _ordersPrev.value = 0
    }

    fun refresh() {
        viewModelScope.launch {
            if (refreshOrders() != 0) {
                getSavedOrders()
            } else {
                reset()
            }
        }
    }

    private suspend fun refreshOrders(): Int {
        return repo.refreshOrders(branch, terminal)
    }

    private fun reset() {
        _orders.value = listOf()
        _ordersNext.value = 0
        _ordersPrev.value = 0
        _totalPage.value = 1
        _currentPage.value = 1
    }

    fun nextPage() {
        if (_ordersNext.value != 0) {
            if (_ordersNext.value!! <= 4) {
                _ordersNext.value = 0
                _currentPage.value = _totalPage.value
            } else {
                _ordersNext.value = _ordersNext.value?.minus(4)
            }
            _ordersPrev.value = _ordersPrev.value?.plus(4)
        }
    }

    fun prevPage() {
        if (_ordersPrev.value!! >= 4) {
            _ordersPrev.value = _ordersPrev.value?.minus(4)
            _currentPage.value = _currentPage.value?.minus(1)
            _ordersNext.value = _orders.value?.let { _ordersNext.value?.plus(it.size) }
        }
    }

    class Factory(val app: Application, private val branch: Int?, private val terminal: Int?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OrdersViewModel(app, branch, terminal) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}