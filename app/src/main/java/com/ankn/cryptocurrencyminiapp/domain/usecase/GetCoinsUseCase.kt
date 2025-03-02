package com.ankn.cryptocurrencyminiapp.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ankn.cryptocurrencyminiapp.data.datasource.source.CoinPagingSource
import com.ankn.cryptocurrencyminiapp.domain.model.Coin
import com.ankn.cryptocurrencyminiapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class GetCoinsUseCase(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(limit: Int, offset: Int): List<Coin> {
        return repository.getCoins(limit, offset)
    }

    fun invokePaging(): Flow<PagingData<Coin>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                CoinPagingSource(repository, isSearch = false, startOffset = 3)
            }
        ).flow
    }
}
