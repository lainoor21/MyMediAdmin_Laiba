package com.fuchsia.mymedicaladmin.api

import com.fuchsia.mymedicaladmin.model.CreateProductResponse
import com.fuchsia.mymedicaladmin.model.DeleteUserResponse
import com.fuchsia.mymedicaladmin.model.GetUserModel
import com.fuchsia.mymedicaladmin.model.SpecificUserResponse
import com.fuchsia.mymedicaladmin.model.UsersModel
import com.fuchsia.mymedicaladmin.model.GetAllOrdersResponse
import com.fuchsia.mymedicaladmin.model.GetAllOrdersResponseItem
import com.fuchsia.mymedicaladmin.model.ProductResponse
import com.fuchsia.mymedicaladmin.model.ProductResponseItem
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST


interface ApiService {

    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<UsersModel>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("user_id") userId: String
    ): Response<SpecificUserResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteUser", hasBody = true)
    suspend fun deleteUser(
        @Field("user_id") userId: String
    ): Response<DeleteUserResponse>

    @FormUrlEncoded
    @PATCH("updateUser")
    suspend fun approveUser(
        @Field("user_id") userId: String,
        @Field("isApproved") isApproved: Int
    ): Response<GetUserModel>

    @FormUrlEncoded
    @PATCH("updateUser")
    suspend fun blockUser(
        @Field("user_id") userId: String,
        @Field("block") isApproved: Int
    ): Response<GetUserModel>

    @FormUrlEncoded
    @PATCH("updateOrder")
    suspend fun updateOrder(
        @Field("order_id") orderId: String,
        @Field("isApproved") isApproved: String,
        @Field("total_price") totalPrice: String,
        @Field("message") message: String,
    ): Response<GetAllOrdersResponseItem>

    @FormUrlEncoded
    @PATCH("updateStock")
    suspend fun updateStockCount(
        @Field("product_id") productId: String,
        @Field("stock") quantity: Int,

    ): Response<ProductResponseItem>

    @FormUrlEncoded
    @POST("createProduct")
    suspend fun createProduct(
        @Field("product_name") productName: String,
        @Field("price") productPrice: Float,
        @Field("stock") productStock: Int,
        @Field("category") productCategory: String,
    ): Response<CreateProductResponse>

    @GET("getAllOrderDetails")
    suspend fun getAllOrderDetails(): Response<GetAllOrdersResponse>


    @FormUrlEncoded
    @POST("getSpecificOrder")
    suspend fun getSpecificOrder(
        @Field("order_id") orderId: String
    ): Response<GetAllOrdersResponseItem>


    @FormUrlEncoded
    @PATCH("updateUser")
    suspend fun updateUserDetails(
        @Field("user_id") user_id: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("address") address: String,
        @Field("phone_number") phone_number: String,
        @Field("pincode") pincode: String,
    ): Response<GetUserModel>


    @GET("getAllProducts")
    suspend fun getAllProducts(): Response<ProductResponse>
}