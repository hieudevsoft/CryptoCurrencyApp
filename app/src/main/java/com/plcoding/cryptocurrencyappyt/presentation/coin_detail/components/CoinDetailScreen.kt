package com.plcoding.cryptocurrencyappyt.presentation.coin_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.plcoding.cryptocurrencyappyt.presentation.coin_detail.CoinDetailViewModel
import com.plcoding.cryptocurrencyappyt.R

@Composable
fun CoinDetailScreen(viewModel: CoinDetailViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        state.coins?.let {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp, 12.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${it.rank}. ${it.name}",
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.weight(8f)
                        )
                        Text(
                            text = if (it.isActive) "active" else "inactive",
                            color = if (it.isActive) Color.Green else Color.Red,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .align(CenterVertically)
                                .weight(2f)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Tags", style = MaterialTheme.typography.h3)
                    Spacer(modifier = Modifier.height(16.dp))
                    FlowRow(
                        mainAxisSpacing = 10.dp,
                        crossAxisSpacing = 10.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        it.tags.forEach { CoinTag(tag = it) }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = LocalContext.current.getString(R.string.team_members), style = MaterialTheme.typography.h3)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(it.team) {
                    TeamListItem(teamMember = it, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp))
                    Divider()
                }

            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}