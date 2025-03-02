package com.ankn.cryptocurrencyminiapp.domain.model

data class Coin(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String?,
    val price: String,
    val change: String?,
    val rank: Int
)
