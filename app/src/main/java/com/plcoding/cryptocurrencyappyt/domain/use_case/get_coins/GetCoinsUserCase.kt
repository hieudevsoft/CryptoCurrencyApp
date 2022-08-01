package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.common.applyCommonSideEffect
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUserCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow<Resource<List<Coin>>> {
        try {
            repository.getCoins().run {
                val coins = repository.getCoins().map { it.toCoin() }
                emit(Resource.Success(coins))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }.applyCommonSideEffect().catch { emit(Resource.Error(it.message)) }
}