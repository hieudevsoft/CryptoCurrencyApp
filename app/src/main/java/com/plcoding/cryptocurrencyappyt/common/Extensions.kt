package com.plcoding.cryptocurrencyappyt.common

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

fun<T> Flow<Resource<T>>.applyCommonSideEffect() = retryWhen { cause, attempt ->
    if(cause is IOException && attempt < Constants.MAX_ENTRIES){
        delay(Utils.getBackOffDelay(attempt.toInt()).toLong())
        true
    } else {
        false
    }
}.onStart { emit(Resource.Loading(null)) }