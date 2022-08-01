package com.plcoding.cryptocurrencyappyt.common

object Utils {
    fun getBackOffDelay(attempt:Int):Int = Constants.INITIAL_BACKOFF *(attempt+1)
}