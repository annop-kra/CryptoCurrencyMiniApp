package com.ankn.cryptocurrencyminiapp.domain.repository

import com.ankn.cryptocurrencyminiapp.domain.model.Coin

interface CoinRepository {
    suspend fun getCoins(limit: Int, offset: Int): List<Coin>
    suspend fun searchCoins(keyword: String, limit: Int, offset: Int): List<Coin>
    suspend fun getCoinDetail(uuid: String): Coin
}
