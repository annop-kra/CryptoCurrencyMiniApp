package com.ankn.cryptocurrencyminiapp.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankn.cryptocurrencyminiapp.R
import com.ankn.cryptocurrencyminiapp.databinding.FragmentHomeBinding
import com.ankn.cryptocurrencyminiapp.presentation.ui.home.adapter.CoinsLoadStateAdapter
import com.ankn.cryptocurrencyminiapp.presentation.ui.home.adapter.OtherCoinsAdapter
import com.ankn.cryptocurrencyminiapp.presentation.ui.home.adapter.TitleAdapter
import com.ankn.cryptocurrencyminiapp.presentation.ui.home.adapter.TopRankCoinAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    // ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()

    private lateinit var titleAdapter: TitleAdapter
    private lateinit var topCoinsAdapter: TopRankCoinAdapter
    private lateinit var otherCoinsAdapter: OtherCoinsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
        observeData()
    }

    private fun setupRecyclerView() {
        topCoinsAdapter = TopRankCoinAdapter()
        titleAdapter = TitleAdapter()
        otherCoinsAdapter = OtherCoinsAdapter { coin ->

        }
        val combinedPagingAdapter = otherCoinsAdapter.withLoadStateFooter(
            footer = CoinsLoadStateAdapter { otherCoinsAdapter.retry() }
        )

        val concatAdapter = ConcatAdapter().apply {
            addAdapter(topCoinsAdapter)
            addAdapter(titleAdapter)
            addAdapter(combinedPagingAdapter)
        }

        binding.recyclerView.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        titleAdapter.submitList(listOf(getString(R.string.other_coin_title)))
    }

    private fun setupSearch() {
        /*    val searchView = binding.toolbar.menu.findItem(R.id.action_search).actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.setSearchQuery(newText ?: "")
                    return true
                }
            })*/
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.topCoins.collect { coins ->
                topCoinsAdapter.submitData(coins)
            }
        }

        lifecycleScope.launch {
            viewModel.coins.collectLatest { pagingData ->
                otherCoinsAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.coinDetail.collect { coin ->
                coin?.let {
                    //showCoinDetail(coin)
                }
            }
        }
    }
}