package com.submission.picodiploma.aplikasigithubusers_navigationapi.utils

sealed class MyResult {
    data class Success<out T>(val list: T): MyResult()
    data class Error(val exception: Throwable): MyResult()
    data class Loading(val isLoading: Boolean): MyResult()
}