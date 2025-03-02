package com.ankn.cryptocurrencyminiapp.domain.usecase

import com.ankn.cryptocurrencyminiapp.domain.model.Coin
import com.ankn.cryptocurrencyminiapp.domain.repository.CoinRepository

class GetCoinDetailUseCase(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(uuid: String): Coin {
        return repository.getCoinDetail(uuid)
    }
}
