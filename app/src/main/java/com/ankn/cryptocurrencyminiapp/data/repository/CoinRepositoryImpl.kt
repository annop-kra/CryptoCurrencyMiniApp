package com.ankn.cryptocurrencyminiapp.data.repository

import com.ankn.cryptocurrencyminiapp.data.datasource.remote.CoinApiService
import com.ankn.cryptocurrencyminiapp.data.mapper.CoinMapper
import com.ankn.cryptocurrencyminiapp.domain.model.Coin
import com.ankn.cryptocurrencyminiapp.domain.repository.CoinRepository

class CoinRepositoryImpl(
    private val api: CoinApiService
) : CoinRepository {

    override suspend fun getCoins(limit: Int, offset: Int): List<Coin> {
        val response = api.getCoins(limit, offset)
        return CoinMapper.mapFromDtoList(response.data.coins)
    }

    override suspend fun searchCoins(keyword: String, limit: Int, offset: Int): List<Coin> {
        val response = api.searchCoins(keyword, limit, offset)
        return CoinMapper.mapFromDtoList(response.data.coins)
    }

    override suspend fun getCoinDetail(uuid: String): Coin {
        val response = api.getCoinDetail(uuid)
        return CoinMapper.mapFromDto(response.data.coin)
    }
}
