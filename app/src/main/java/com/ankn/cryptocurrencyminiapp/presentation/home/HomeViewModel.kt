package com.ankn.cryptocurrencyminiapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ankn.cryptocurrencyminiapp.presentation.navigation.NavigationDestination

import com.ankn.cryptocurrencyminiapp.presentation.navigation.Navigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest


class HomeViewModel(
    private val navigator: Navigator,
   // private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    fun onItemClicked(itemTitle: String) {
        navigator.navigateTo(NavigationDestination.Search)
    }

/*    val searchQuery = MutableStateFlow("")

    val itemsPagingDataFlow: Flow<PagingData<Comment>> = searchQuery
        .debounce(300) // รอการพิมพ์ให้เสร็จ
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getCommentsUseCase()
        }
        .cachedIn(viewModelScope)

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }*/
}
