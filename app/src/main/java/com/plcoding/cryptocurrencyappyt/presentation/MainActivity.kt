package com.plcoding.cryptocurrencyappyt.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.common.LocaleUtils
import com.plcoding.cryptocurrencyappyt.presentation.coin_detail.components.CoinDetailScreen
import com.plcoding.cryptocurrencyappyt.presentation.coin_list.components.CoinListScreen
import com.plcoding.cryptocurrencyappyt.ui.theme.CryptocurrencyAppYTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleUtils.onAttachWithBaseContext(newBase,Constants.LANGUAGE))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleUtils.setLocale(this,"en")
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val isDarkTheme = mainViewModel.stateIsDarkTheme.value
            CryptocurrencyAppYTTheme(darkTheme = isDarkTheme) {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.CoinListScreen.route){
                        composable(route = Screen.CoinListScreen.route){
                            CoinListScreen(navController){
                                mainViewModel.setIsDarkTheme(it)
                                if(!it) Constants.LANGUAGE = "vi" else Constants.LANGUAGE = "en"
                                recreate()
                            }
                        }
                        composable(route = Screen.CoinDetailScreen.route+"/{coinId}"){
                            CoinDetailScreen()
                        }
                    }
                }
            }
        }
    }
}