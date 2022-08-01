package com.plcoding.cryptocurrencyappyt.common

sealed class Resource<out T>(open val data:T?=null, open var message:String?=null){
    data class Loading<T>(override val data:T?=null):Resource<T>(data)
    data class Success<T>(override val data:T):Resource<T>(data)
    data class Error<T>(override var message:String?=null):Resource<T>(null,message)
}