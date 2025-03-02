package com.ankn.cryptocurrencyminiapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ankn.cryptocurrencyminiapp.domain.model.Coin
import com.ankn.cryptocurrencyminiapp.domain.usecase.GetCoinDetailUseCase
import com.ankn.cryptocurrencyminiapp.domain.usecase.GetCoinsUseCase
import com.ankn.cryptocurrencyminiapp.domain.usecase.SearchCoinsUseCase
import com.ankn.cryptocurrencyminiapp.presentation.navigation.NavigationDestination

import com.ankn.cryptocurrencyminiapp.presentation.navigation.Navigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val searchCoinsUseCase: SearchCoinsUseCase,
    private val getCoinDetailUseCase: GetCoinDetailUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _coinDetail = MutableStateFlow<Coin?>(null)
    val coinDetail = _coinDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    val topCoins = flow {
        _isLoading.value = true
        try {
            val coins = getCoinsUseCase(3, 0)
            emit(coins)
        } catch (e: Exception) {
            _error.value = e.message ?: "Failed to load top coins"
        } finally {
            _isLoading.value = false
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val coins = searchQuery
        .debounce(1000) // Debounce 1 second
        .flatMapLatest { query ->
            _isLoading.value = true
            if (query.isEmpty()) {
                getCoinsUseCase.invokePaging().cachedIn(viewModelScope)
            } else {
                searchCoinsUseCase.invokePaging(query).cachedIn(viewModelScope)
            }
        }
        .onEach { _isLoading.value = false }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun loadCoinDetail(uuid: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val coin = getCoinDetailUseCase(uuid)
                _coinDetail.value = coin
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load coin detail"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
