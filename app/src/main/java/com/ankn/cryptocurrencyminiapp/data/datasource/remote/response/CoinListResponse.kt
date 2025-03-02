package com.ankn.cryptocurrencyminiapp.data.datasource.remote.response

import com.squareup.moshi.Json

data class CoinListResponse(
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: CoinListData
)

data class CoinListData(
    @Json(name = "stats") val stats: CoinStats,
    @Json(name = "coins") val coins: List<CoinDto>
)

data class CoinStats(
    @Json(name = "total") val total: Int,
    @Json(name = "totalCoins") val totalCoins: Int,
    @Json(name = "totalMarkets") val totalMarkets: Int,
    @Json(name = "totalExchanges") val totalExchanges: Int,
    @Json(name = "totalMarketCap") val totalMarketCap: String,
    @Json(name = "total24hVolume") val total24hVolume: String
)

data class CoinDto(
    @Json(name = "uuid") val uuid: String,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "name") val name: String,
    @Json(name = "color") val color: String?,
    @Json(name = "iconUrl") val iconUrl: String?,
    @Json(name = "marketCap") val marketCap: String,
    @Json(name = "price") val price: String,
    @Json(name = "listedAt") val listedAt: Long,
    @Json(name = "change") val change: String?,
    @Json(name = "rank") val rank: Int,
    @Json(name = "sparkline") val sparkline: List<String>?,
    @Json(name = "lowVolume") val lowVolume: Boolean?,
    @Json(name = "coinrankingUrl") val coinrankingUrl: String?,
    @Json(name = "24hVolume") val volume24h: String?,
    @Json(name = "btcPrice") val btcPrice: String?,
    @Json(name = "contractAddresses") val contractAddresses: List<String>?
)