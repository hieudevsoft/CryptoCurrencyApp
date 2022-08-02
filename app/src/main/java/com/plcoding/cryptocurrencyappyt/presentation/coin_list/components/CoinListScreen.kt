package com.plcoding.cryptocurrencyappyt.presentation.coin_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.plcoding.cryptocurrencyappyt.R
import com.plcoding.cryptocurrencyappyt.presentation.Screen
import com.plcoding.cryptocurrencyappyt.presentation.coin_list.CoinListViewModel

@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel(),
    isDarkTheme: (Boolean) -> Unit
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        val isEnabled = remember {
            mutableStateOf(false)
        }
        val isChecked = viewModel.stateSwitchIsChecked.value
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.dark_theme),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Switch(
                        checked = isChecked, onCheckedChange = {
                            isDarkTheme(it)
                            viewModel.setStateSwitch(it)
                        }, modifier = Modifier.padding(12.dp), colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Yellow,
                            checkedTrackColor = Color.Yellow,
                            uncheckedThumbColor = Color.DarkGray,
                            uncheckedTrackColor = Color.DarkGray,
                            disabledUncheckedThumbColor = Color.LightGray,
                            disabledUncheckedTrackColor = Color.LightGray,
                            disabledCheckedThumbColor = Color.LightGray,
                            disabledCheckedTrackColor = Color.LightGray,
                            checkedTrackAlpha = 0.6f,
                            uncheckedTrackAlpha = 0.5f
                        ), enabled = isEnabled.value
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(state.coins) {
                CoinListItem(coin = it) {
                    navController.navigate(Screen.CoinDetailScreen.route + "/${it.id}")
                }
            }
        }
        if (state.error.isNotBlank()) {
            isEnabled.value = false
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center)
            )
        } else isEnabled.value = true
        if (state.isLoading) {
            isEnabled.value = false
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}