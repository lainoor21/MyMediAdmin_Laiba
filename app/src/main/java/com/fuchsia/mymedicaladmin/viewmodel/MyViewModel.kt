package com.fuchsia.mymedicaladmin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuchsia.mymedicaladmin.repo.Repo
import com.fuchsia.mymedicaladmin.common.Results
import com.fuchsia.mymedicaladmin.model.CreateProductResponse
import com.fuchsia.mymedicaladmin.model.DeleteUserResponse
import com.fuchsia.mymedicaladmin.model.GetUserModel
import com.fuchsia.mymedicaladmin.model.SpecificUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    private val _Get_All_User_state = MutableStateFlow(GetAllUserState())
    val Get_All_User_state = _Get_All_User_state.asStateFlow()

     fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllUsers().collect {
                when (it) {
                    is Results.Loading -> {
                        _Get_All_User_state.value = GetAllUserState(isLoading = true)
                    }

                    is Results.Error -> {
                        _Get_All_User_state.value = GetAllUserState(error = it.message)

                    }

                    is Results.Success -> {
                        _Get_All_User_state.value = GetAllUserState(data = it.data.body())
                    }

                }

            }

        }
    }

    fun removeUserFromPending(userId: String) {
        val updatedList = _Get_All_User_state.value.data?.filter { it.user_id != userId.toString() }
        _Get_All_User_state.value = _Get_All_User_state.value.copy(data = updatedList)
    }

    private val _getSpecificUserState = MutableStateFlow(GetSpecificUserState())
    val getSpecificUserState = _getSpecificUserState.asStateFlow()

    fun getSpecificUser(user_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificUser(user_id).collect {
                when (it) {
                    is Results.Loading -> {
                        _getSpecificUserState.value = GetSpecificUserState(isLoading = true)
                    }

                    is Results.Error -> {
                        _getSpecificUserState.value =
                            GetSpecificUserState(error = it.message, isLoading = false)
                    }

                    is Results.Success -> {
                        _getSpecificUserState.value =
                            GetSpecificUserState(data = it.data.body(), isLoading = false)
                    }
                }
            }
        }
    }

    private val _updateUserState = MutableStateFlow(UpdateUserState())
    val updateUserState = _updateUserState.asStateFlow()

    fun approveUser(user_id: String, isApproved: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.approveUser(user_id, isApproved).collect {
                when (it) {
                    is Results.Loading -> {
                        _updateUserState.value = UpdateUserState(isLoading = true)
                    }

                    is Results.Error -> {
                        _updateUserState.value =
                            UpdateUserState(error = it.message, isLoading = false)
                    }

                    is Results.Success -> {
                        _updateUserState.value =
                            UpdateUserState(data = it.data.body(), isLoading = false)
                    }
                }
            }
        }
    }

    private val _GetUpdateState = MutableStateFlow(UpdateUserState())

    fun updateUsers(
        user_id: String,
        name: String,
        email: String,
        password: String,
        address: String,
        phone_number: String,
        pincode: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateUser(user_id,name,email, password, address, pincode, phone_number).collect{
                when(it){
                    is Results.Loading-> {
                        _GetUpdateState.value = UpdateUserState(isLoading = true)
                    }
                    is Results.Error ->{
                        _GetUpdateState.value = UpdateUserState(error = it.message, isLoading = true)

                    }
                    is Results.Success ->{

                        _GetUpdateState.value = UpdateUserState(data = it.data.body(), isLoading = true)

                    }
                }
            }

        }
    }

    private val _addProductState = MutableStateFlow(AddProductState())
    val addProductState = _addProductState.asStateFlow()

    fun addProduct(
        productName: String,
        productPrice: Float,
        productStock: Int,
        productCategory: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            repo.createProduct(productName, productPrice, productStock, productCategory).collect {
                when (it) {

                    is Results.Loading -> {
                        _addProductState.value = AddProductState(isLoading = true)
                    }

                    is Results.Error -> {
                        _addProductState.value = AddProductState(error = it.message)
                    }

                    is Results.Success -> {
                        _addProductState.value = AddProductState(data = it.data.body())
                    }

                }
            }

        }
    }

    private val _deleteUserState = MutableStateFlow(DeleteUserState())


    fun deleteUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteUser(userId).collect {
                when (it) {
                    is Results.Loading -> {
                        _deleteUserState.value = DeleteUserState(isLoading = true)
                    }

                    is Results.Error -> {
                        _deleteUserState.value = DeleteUserState(error = it.message)
                    }

                    is Results.Success -> {
                        _deleteUserState.value = DeleteUserState(data = it.data.body())
                    }
                }
            }
        }

    }

    data class GetAllUserState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val data: List<GetUserModel>? = null


    )

    data class GetSpecificUserState(
        val isLoading: Boolean = false,
        val data: SpecificUserResponse? = null,
        val error: String? = null
    )

    data class UpdateUserState(
        val isLoading: Boolean = false,
        val data: GetUserModel? = null,
        val error: String? = null

    )

    data class AddProductState(
        val isLoading: Boolean = false,
        val data: CreateProductResponse? = null,
        val error: String? = null

    )

    data class DeleteUserState(
        val isLoading: Boolean = false,
        val data: DeleteUserResponse? = null,
        val error: String? = null
    )
}