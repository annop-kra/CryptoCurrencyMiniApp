package com.ankn.cryptocurrencyminiapp.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ankn.cryptocurrencyminiapp.data.datasource.source.CoinPagingSource
import com.ankn.cryptocurrencyminiapp.domain.model.Coin
import com.ankn.cryptocurrencyminiapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class SearchCoinsUseCase(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(query: String, limit: Int, offset: Int): List<Coin> {
        return repository.searchCoins(query, limit, offset)
    }

    fun invokePaging(query: String): Flow<PagingData<Coin>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CoinPagingSource(repository, isSearch = true, query = query) }
        ).flow
    }
}
