package com.example.android.chefapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.chefapp.database.getDatabase
import com.example.android.chefapp.domain.Order
import com.example.android.chefapp.domain.OrderItem
import com.example.android.chefapp.repository.HistoryRepository

class HistoryViewModel(application: Application, val terminal: Int?, val branch: Int?) :
    AndroidViewModel(application) {

    val database = getDatabase(application)
    val repo = HistoryRepository(database)

    private var allOrders = listOf<Order>()

    private val _orders = MutableLiveData(
        listOf(
            Order(
                no = 1,
                serial = "KDSN745",
                date = "20/09/2014",
                time = "23:34",
                note = "",
                status = "New",
                type = "Done",
                passedTime = "5:44",
                items = listOf(
                    OrderItem(
                        name = "Milkshake",
                        quantity = "4",
                        status = "New"
                    )
                )
            ),
            Order(
                no = 2,
                serial = "KDSN745",
                date = "20/09/2014",
                time = "23:34",
                note = "",
                status = "New",
                type = "Delivery",
                passedTime = "5:44",
                items = listOf(
                    OrderItem(
                        name = "",
                        quantity = "4",
                        status = "New"
                    )
                )
            )
        )
    )
    val orders: LiveData<List<Order>>
        get() = _orders

    private val _selectedOrder = MutableLiveData<Order>()
    val selectedOrder: LiveData<Order>
        get() = _selectedOrder

    private val _doneOrders = MutableLiveData<List<Order>>()
    val doneOrders: LiveData<List<Order>>
        get() = _doneOrders

    private val _selectOrderType = MutableLiveData<Int>()
    val selectOrderType: LiveData<Int>
        get() = _selectOrderType

    private val _canceledOrders = MutableLiveData<List<Order>>()
    val canceledOrders: LiveData<List<Order>>
        get() = _canceledOrders

    init {
        allOrders = _orders.value!!
        _selectedOrder.value = _orders.value?.get(0)
        _doneOrders.value = _orders.value?.filter { it.status == "New" }
        _canceledOrders.value = _orders.value?.filter { it.status == "Canceled" }
    }


    fun selectOrder(no: Int) {
        _selectedOrder.value = _orders.value?.find { it.no == no }
    }

    fun selectOrderType(no: Int) {
        _selectOrderType.value = no
        when (no) {
            1 -> _orders.value = allOrders
            2 -> _orders.value = allOrders.filter { it.status == "New" }
            else -> _orders.value = allOrders.filter { it.status == "Canceled" }
        }
    }


    class Factory(val app: Application, val terminal: Int?, val branch: Int?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HistoryViewModel(app, terminal, branch) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}