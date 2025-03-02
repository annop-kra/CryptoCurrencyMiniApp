package com.ankn.cryptocurrencyminiapp.data.datasource.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ankn.cryptocurrencyminiapp.data.datasource.remote.CoinApiService
import com.ankn.cryptocurrencyminiapp.data.datasource.remote.response.CoinDto
import com.ankn.cryptocurrencyminiapp.domain.model.Coin
import com.ankn.cryptocurrencyminiapp.domain.repository.CoinRepository


class CoinPagingSource(
    private val repository: CoinRepository,
    private val isSearch: Boolean,
    private val query: String? = null,
    private val startOffset: Int = 0
) : PagingSource<Int, Coin>() {

    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        return try {
            val page = params.key ?: 0
            val limit = params.loadSize
            val offset = (page * limit) + startOffset

            val coins = if (isSearch) {
                repository.searchCoins(query ?: "", limit, offset)
            } else {
                repository.getCoins(limit, offset)
            }

            LoadResult.Page(
                data = coins,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (coins.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
