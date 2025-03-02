package com.ankn.cryptocurrencyminiapp.di

import com.ankn.cryptocurrencyminiapp.domain.repository.CoinRepository
import com.ankn.cryptocurrencyminiapp.domain.usecase.GetCoinDetailUseCase
import com.ankn.cryptocurrencyminiapp.domain.usecase.GetCoinsUseCase
import com.ankn.cryptocurrencyminiapp.domain.usecase.SearchCoinsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetCoinsUseCase(get()) }
    factory { SearchCoinsUseCase(get()) }
    factory { GetCoinDetailUseCase(get()) }
}