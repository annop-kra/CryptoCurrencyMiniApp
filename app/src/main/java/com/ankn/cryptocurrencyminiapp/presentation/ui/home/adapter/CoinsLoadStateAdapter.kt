package com.ankn.cryptocurrencyminiapp.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ankn.cryptocurrencyminiapp.databinding.ItemLoadStateBinding

class CoinsLoadStateAdapter (
    private val retry: () -> Unit
) : LoadStateAdapter<CoinsLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding, retry)
    }

    class LoadStateViewHolder(private val binding: ItemLoadStateBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Loading) {
                progressBar.visibility = View.VISIBLE
                btnRetry.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                btnRetry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            }
        }
    }
}
