package com.ankn.cryptocurrencyminiapp.presentation.navigation

interface Navigator {
    fun navigateTo(destination: NavigationDestination)
    fun navigateBack()
}