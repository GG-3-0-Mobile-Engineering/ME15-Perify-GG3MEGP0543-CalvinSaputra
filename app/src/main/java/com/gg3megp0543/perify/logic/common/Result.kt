package com.gg3megp0543.perify.logic.common

sealed class ApiRes<out T> {
    data class Success<out T>(val data: T): ApiRes<T>()
    data class Error(val throwable: Throwable): ApiRes<Nothing>()
}