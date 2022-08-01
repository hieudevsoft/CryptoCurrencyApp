package com.plcoding.cryptocurrencyappyt.di

import com.plcoding.cryptocurrencyappyt.data.repository.CoinRepositoryImpl
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProvideInterfaceModule {
    @Binds
    abstract fun bindCoinRepository(coinRepositoryImpl: CoinRepositoryImpl):CoinRepository
}