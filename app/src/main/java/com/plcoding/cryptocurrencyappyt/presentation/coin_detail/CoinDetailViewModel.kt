package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin.GetCoinDetailUserCase
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins.GetCoinsUserCase
import com.plcoding.cryptocurrencyappyt.presentation.coin_list.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

const val PARAM_COIN_ID = "COIN_ID"
@HiltViewModel
class CoinDetailViewModel(app: Application, private val getCoinUseCase: GetCoinDetailUserCase, saveStateHandle:SavedStateHandle) : AndroidViewModel(app) {
    private val _stateCoinDetail = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _stateCoinDetail

    init {
        saveStateHandle.get<String>(PARAM_COIN_ID)?.let { coinId->getCoins(coinId) }
    }

    private fun getCoins(coinId:String) {
        getCoinUseCase.invoke(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateCoinDetail.value = CoinDetailState(coins = result.data)
                }

                is Resource.Error -> {
                    _stateCoinDetail.value =
                        _stateCoinDetail.value.copy(error = result.message ?: "An unexpected error")
                }

                is Resource.Loading -> {
                    _stateCoinDetail.value = CoinDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}