package com.fuchsia.mymedicaladmin.repo

import android.util.Log
import com.fuchsia.mymedicaladmin.api.ApiBuilder
import com.fuchsia.mymedicaladmin.common.Results
import com.fuchsia.mymedicaladmin.model.CreateProductResponse
import com.fuchsia.mymedicaladmin.model.DeleteUserResponse
import com.fuchsia.mymedicaladmin.model.GetUserModel
import com.fuchsia.mymedicaladmin.model.SpecificUserResponse
import com.fuchsia.mymedicaladmin.model.UsersModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class Repo @Inject constructor(private val apiBuilder: ApiBuilder) {

    suspend fun getAllUsers(): Flow<Results<Response<UsersModel>>> = flow {
        emit(Results.Loading)
        try {
            val response = apiBuilder.api.getAllUsers()
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))

        }
    }

    suspend fun getSpecificUser(id: String): Flow<Results<Response<SpecificUserResponse>>> = flow {

        emit(Results.Loading)

        try {
            val response = apiBuilder.api.getSpecificUser(id)
            if (response.isSuccessful) {
                emit(Results.Success(response))
            } else {
                emit(Results.Error("Error: ${response.code()} - ${response.message()}"))
            }

        }
        catch (e: Exception) {
            emit(Results.Error(e.message.toString()))

        }

    }


    suspend fun approveUser(id: String, isApproved: Int): Flow<Results<Response<GetUserModel>>> =
        flow {
            emit(Results.Loading)
            try {
                val response = apiBuilder.api.approveUser(id, isApproved)
                if (response.isSuccessful) {
                    emit(Results.Success(response))
                } else {
                    emit(Results.Error("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Results.Error(e.message.toString()))
            }

        }

    suspend fun createProduct(
        productName: String,
        productPrice: Float,
        productStock: Int,
        productCategory: String

    ): Flow<Results<Response<CreateProductResponse>>> = flow{
        emit(Results.Loading)

        try {
            val response =apiBuilder.api.createProduct(productName, productPrice, productStock, productCategory)
            emit(Results.Success(response))

        }
        catch (exception: Exception){
            emit(Results.Error(exception.message.toString()))
        }

    }

    suspend fun deleteUser(userId: String): Flow<Results<Response<DeleteUserResponse>>> = flow{
        emit(Results.Loading)
        try {
            val response = apiBuilder.api.deleteUser(userId)
            emit(Results.Success(response))
        }
        catch (exception: Exception){
            emit(Results.Error(exception.message.toString()))
        }

    }


    suspend fun updateUser(
        user_id: String,
        name: String,
        email: String,
        password: String,
        address: String,
        pincode: String,
        phone_number: String

    ): Flow<Results<Response<GetUserModel>>> = flow {
        emit(Results.Loading)
        try {
            val response = apiBuilder.api.updateUserDetails(user_id,name,email,password,address,pincode,phone_number)
            emit(Results.Success(response))
        }
        catch (exception: Exception){
            emit(Results.Error(exception.message.toString()))
        }
    }
}