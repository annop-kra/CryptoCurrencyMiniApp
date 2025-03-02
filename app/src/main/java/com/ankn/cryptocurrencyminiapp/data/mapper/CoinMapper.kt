package com.ankn.cryptocurrencyminiapp.data.mapper

import com.ankn.cryptocurrencyminiapp.data.datasource.remote.response.CoinDto
import com.ankn.cryptocurrencyminiapp.domain.model.Coin

object CoinMapper {
    fun mapFromDto(dto: CoinDto): Coin {
        return Coin(
            uuid = dto.uuid,
            symbol = dto.symbol,
            name = dto.name,
            iconUrl = dto.iconUrl,
            price = dto.price,
            change = dto.change,
            rank = dto.rank
        )
    }

    fun mapFromDtoList(dtoList: List<CoinDto>): List<Coin> {
        return dtoList.map { mapFromDto(it) }
    }
}
