package com.ankn.cryptocurrencyminiapp.di

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ankn.cryptocurrencyminiapp.presentation.home.HomeViewModel
import com.ankn.cryptocurrencyminiapp.presentation.navigation.Navigator

import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (activity: AppCompatActivity, navController: NavController) ->
        HomeViewModel(get<Navigator> { parametersOf(activity, navController) })
    }
}