package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.common.applyCommonSideEffect
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoinDetail
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinDetailUserCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke(coinId:String): Flow<Resource<CoinDetail>> = flow<Resource<CoinDetail>> {
        try {
            repository.getCoins().run {
                val coin = repository.getCoinById(coinId).toCoinDetail()
                emit(Resource.Success(coin))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }.applyCommonSideEffect().catch { emit(Resource.Error(it.message)) }
}