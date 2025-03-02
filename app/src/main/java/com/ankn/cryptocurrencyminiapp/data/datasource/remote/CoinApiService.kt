package com.ankn.cryptocurrencyminiapp.data.datasource.remote

import com.ankn.cryptocurrencyminiapp.data.datasource.remote.response.CoinDetailResponse
import com.ankn.cryptocurrencyminiapp.data.datasource.remote.response.CoinListResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApiService {

    @GET("v2/coins")
    suspend fun getCoins(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): CoinListResponse

    @POST("v2/coins")
    suspend fun searchCoins(
        @Query("search") keyword: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): CoinListResponse

    @GET("v2/coin/{uuid}")
    suspend fun getCoinDetail(
        @Path("uuid") uuid: String
    ): CoinDetailResponse
}
