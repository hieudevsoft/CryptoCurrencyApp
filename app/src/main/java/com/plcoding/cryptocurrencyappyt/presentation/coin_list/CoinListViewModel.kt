package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins.GetCoinsUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(app: Application, private val getCoinsUseCase:GetCoinsUserCase,savedStateHandle: SavedStateHandle):AndroidViewModel(app) {

    private val _stateCoinList = mutableStateOf(CoinListState())
    val state:State<CoinListState> = _stateCoinList

    private val _stateSwitchIsChecked = mutableStateOf(true)
    val stateSwitchIsChecked:State<Boolean> = _stateSwitchIsChecked
    init {
        getCoins()
        savedStateHandle.get<Boolean>("isChecked")?.let { _stateSwitchIsChecked.value=it }
    }

    private fun getCoins() {
        getCoinsUseCase.invoke().onEach {result->
            when(result){
                is Resource.Success->{
                    _stateCoinList.value = CoinListState(coins = result.data)
                }

                is Resource.Error->{
                    _stateCoinList.value = _stateCoinList.value.copy(error = result.message?:"An unexpected error")
                }

                is Resource.Loading->{
                    _stateCoinList.value = CoinListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setStateSwitch(isChecked:Boolean) {
        _stateSwitchIsChecked.value = isChecked
    }
}