package com.ankn.cryptocurrencyminiapp.presentation.navigation

sealed class NavigationDestination {
    object Search : NavigationDestination()
    object Home : NavigationDestination()
}