package com.fuchsia.mymedicaladmin.repo

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import com.fuchsia.mymedicaladmin.api.ApiBuilder
import com.fuchsia.mymedicaladmin.common.Results
import com.fuchsia.mymedicaladmin.model.GetAllOrdersResponse
import com.fuchsia.mymedicaladmin.model.GetAllOrdersResponseItem
import com.fuchsia.mymedicaladmin.model.ProductResponse
import com.fuchsia.mymedicaladmin.model.ProductResponseItem
import com.fuchsia.mymedicaladmin.model.SpecificUserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ProductRepo @Inject constructor(private val apiBuilder: ApiBuilder) {


    suspend fun get_AllOrdersHistory(): Flow<Results<Response<GetAllOrdersResponse>>> = flow {

        emit(Results.Loading)
        try {
            val response = apiBuilder.api.getAllOrderDetails()
            emit(Results.Success(response))

        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }

    }

    suspend fun get_SpecificOrder(orderId: String): Flow<Results<Response<GetAllOrdersResponseItem>>> =
        flow {

            emit(Results.Loading)

            try {
                val response = apiBuilder.api.getSpecificOrder(orderId)
                if (response.isSuccessful) {
                    emit(Results.Success(response))
                    Log.d(TAG, "get_SpecificOrder: $response")
                } else {
                    emit(Results.Error("Error: ${response.code()} - ${response.message()}"))
                }

            } catch (e: Exception) {
                emit(Results.Error(e.message.toString()))

            }

        }

    suspend fun updateOrder(
        orderId: String,
        isApproved: String,
        totalPrice: String,
        message: String
    ): Flow<Results<Response<GetAllOrdersResponseItem>>> =
        flow {
            emit(Results.Loading)
            try {
                val response = apiBuilder.api.updateOrder(orderId, isApproved, totalPrice, message)
                emit(Results.Success(response))
                Log.d(TAG, "updateOrder: $response")
            } catch (e: Exception) {
                emit(Results.Error(e.message.toString()))
            }

        }

    suspend fun update_Stock(
        productId: String,
        quantity: Int
    ): Flow<Results<Response<ProductResponseItem>>> = flow {
        emit(Results.Loading)
        try {
            val response = apiBuilder.api.updateStockCount(
                productId,
                quantity
            )
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))

        }

    }

    suspend fun getAllProducts(): Flow<Results<Response<ProductResponse>>> = flow {

        emit(Results.Loading)
        try {
            val response = apiBuilder.api.getAllProducts()
            emit(Results.Success(response))

        }catch ( e: Exception){
            emit(Results.Error(e.message.toString()))
        }

    }

}