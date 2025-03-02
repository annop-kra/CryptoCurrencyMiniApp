package com.ankn.cryptocurrencyminiapp.di

import com.ankn.cryptocurrencyminiapp.presentation.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get(), get())
    }
}