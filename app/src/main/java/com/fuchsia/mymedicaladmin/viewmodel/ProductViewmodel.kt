package com.fuchsia.mymedicaladmin.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuchsia.mymedicaladmin.common.Results
import com.fuchsia.mymedicaladmin.model.CreateProductResponse
import com.fuchsia.mymedicaladmin.repo.ProductRepo
import com.fuchsia.mymedicaladmin.model.GetAllOrdersResponse
import com.fuchsia.mymedicaladmin.model.GetAllOrdersResponseItem
import com.fuchsia.mymedicaladmin.model.ProductResponse
import com.fuchsia.mymedicaladmin.model.ProductResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewmodel @Inject constructor(private val productRepo: ProductRepo) : ViewModel() {

    private val _getAllOrdersHistoryState = MutableStateFlow(GetAllOrdersState())
    val getAllOrdersHistoryState = _getAllOrdersHistoryState.asStateFlow()

    suspend fun getAllOrdersHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.get_AllOrdersHistory().collect {
                when (it) {
                    is Results.Loading -> {
                        _getAllOrdersHistoryState.value = GetAllOrdersState(isLoading = true)
                    }

                    is Results.Error -> {
                        _getAllOrdersHistoryState.value = GetAllOrdersState(error = it.message)

                    }

                    is Results.Success -> {
                        _getAllOrdersHistoryState.value = GetAllOrdersState(data = it.data.body())
                    }

                }

            }

        }
    }

    private val _getSpecificOrderState = MutableStateFlow(GetSpecificOrderState())
    val getSpecificOrderState = _getSpecificOrderState.asStateFlow()

    suspend fun getSpecificOrder(orderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.get_SpecificOrder(orderId).collect {
                when (it) {
                    is Results.Loading -> {
                        _getSpecificOrderState.value = GetSpecificOrderState(isLoading = true)
                    }

                    is Results.Error -> {
                        _getSpecificOrderState.value = GetSpecificOrderState(error = it.message)
                    }

                    is Results.Success -> {
                        _getSpecificOrderState.value = GetSpecificOrderState(data = it.data.body())
                        Log.d(TAG, "getSpecificOrder: ${it.data.body()}")
                    }
                }
            }

        }
    }

    private val _updateOrderState = MutableStateFlow(GetSpecificOrderState())
    fun updateOrder(
        orderId: String,
        isApproved: String,
        totalPrice: String,
        message: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.updateOrder(
                orderId,
                isApproved,
                totalPrice,
                message

            ).collect {
                when (it) {
                    is Results.Loading -> {
                        _updateOrderState.value = GetSpecificOrderState(isLoading = true)
                    }

                    is Results.Error -> {
                        _updateOrderState.value = GetSpecificOrderState(error = it.message)
                    }

                    is Results.Success -> {
                        _updateOrderState.value = GetSpecificOrderState(data = it.data.body())
                    }
                }


            }

        }
    }

    private val _updateStockState = MutableStateFlow(GetUpdateStockState())
    fun update_Stock(
        productId: String,
        quantity: Int
    ){
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.update_Stock(productId, quantity).collect { result ->
                when (result) {
                    is Results.Loading -> {
                        _updateStockState.value = GetUpdateStockState(isLoading = true)
                        Log.d("UpdateStock", "Loading...")
                    }
                    is Results.Success -> {
                        val responseData = result.data.body()
                        _updateStockState.value = GetUpdateStockState(data = responseData)
                        Log.d("UpdateStock", "Success: $responseData")
                    }
                    is Results.Error -> {
                        _updateStockState.value = GetUpdateStockState(error = result.message)
                        Log.e("UpdateStock", "Error: ${result.message}")
                    }
                }
            }
        }
    }



    private val _getAllProductsState= MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()

    suspend fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.getAllProducts().collect {
                when (it) {
                    is Results.Loading -> {
                        _getAllProductsState.value = GetAllProductsState(isLoading = true)
                    }

                    is Results.Error -> {
                        _getAllProductsState.value = GetAllProductsState(error = it.message)

                    }

                    is Results.Success -> {
                        _getAllProductsState.value = GetAllProductsState(data = it.data.body())
                    }

                }

            }

        }
    }
}

data class GetAllOrdersState(
    val isLoading: Boolean = false,
    val data: GetAllOrdersResponse? = null,
    val error: String? = null

)

data class GetSpecificOrderState(
    val isLoading: Boolean = false,
    val data: GetAllOrdersResponseItem? = null,
    val error: String? = null
)

data class GetUpdateStockState(
    val isLoading: Boolean = false,
    val data: ProductResponseItem? = null,
    val error: String? = null
)

data class GetAllProductsState(
    val isLoading: Boolean = false,
    val data: ProductResponse? = null,
    val error: String?= null
)