package com.plcoding.cryptocurrencyappyt.presentation

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(app:Application,savedStateHandle: SavedStateHandle):AndroidViewModel(app) {
    private val _isDarkThemeState = mutableStateOf(true)
    val stateIsDarkTheme: State<Boolean> = _isDarkThemeState
    init {
        savedStateHandle.get<Boolean>("isDarkTheme")?.let { _isDarkThemeState.value = it }
    }
    fun setIsDarkTheme(isDarkTheme:Boolean){
        _isDarkThemeState.value = isDarkTheme
    }
}