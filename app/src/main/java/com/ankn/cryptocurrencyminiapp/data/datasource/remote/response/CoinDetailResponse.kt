package com.ankn.cryptocurrencyminiapp.data.datasource.remote.response

import com.squareup.moshi.Json

data class CoinDetailResponse(
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: CoinDetailData
)

data class CoinDetailData(
    @Json(name = "coin") val coin: CoinDto
)