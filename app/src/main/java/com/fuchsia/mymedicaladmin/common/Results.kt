package com.fuchsia.mymedicaladmin.common

sealed class Results<out T> {

    data object Loading : Results<Nothing>()
    data class Success<out T> (val data:T): Results<T>()
    data class Error(val message: String): Results<Nothing>()
}