package com.ankn.cryptocurrencyminiapp.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.ankn.cryptocurrencyminiapp.R
import com.ankn.cryptocurrencyminiapp.databinding.ItemTopRankBinding
import com.ankn.cryptocurrencyminiapp.domain.model.Coin
import com.ankn.cryptocurrencyminiapp.presentation.utils.extensions.setColoredText
import kotlin.math.absoluteValue

class TopRankCoinAdapter : RecyclerView.Adapter<TopRankCoinAdapter.TopRankViewHolder>() {

    private var coinList: List<Coin> = emptyList()

    fun submitData(coins: List<Coin>) {
        coinList = coins
        notifyDataSetChanged()
    }

    inner class TopRankViewHolder(private val binding: ItemTopRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            with(binding) {

                // Use the extension function
                titleText.setColoredText(
                    fullText = binding.root.context.getString(R.string.top_rank_title),
                    targetText = "3",
                    primaryColor = ContextCompat.getColor(binding.root.context, R.color.text_main),
                    targetColor = ContextCompat.getColor(binding.root.context, R.color.red),
                    boldTarget = false
                )

                // Bind data for each coin position
                bindCoinView(
                    0,
                    imgCoinFirst,
                    tvCoinSymbolFirst,
                    tvCoinNameFirst,
                    tvChangeFirst,
                    ivArrowFirst
                )
                bindCoinView(
                    1,
                    imgCoinSecond,
                    tvCoinSymbolSecond,
                    tvCoinNameSecond,
                    tvChangeSecond,
                    ivArrowSecond
                )
                bindCoinView(
                    2,
                    imgCoinThird,
                    tvCoinSymbolThird,
                    tvCoinNameThird,
                    tvChangeThird,
                    ivArrowThird
                )
            }
        }

        private fun bindCoinView(
            index: Int,
            imageView: ImageView,
            symbolView: TextView,
            nameView: TextView,
            changeView: TextView,
            arrowView: ImageView
        ) {
            coinList.getOrNull(index)?.let { coin ->
                // Load image using Coil with SVG support
                loadSvgOrBitmap(coin.iconUrl, imageView)

                symbolView.text = coin.symbol
                nameView.text = coin.name
                val changeText = coin.change?.toDoubleOrNull()?.let {
                    "${it.absoluteValue}%"
                } ?: "N/A"

                changeView.text = changeText

                val isUp = (coin.change?.toDoubleOrNull() ?: 0.0) >= 0.0
                arrowView.setImageResource(if (isUp) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
                changeView.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        if (isUp) R.color.green else R.color.red
                    )
                )
            }
        }

        private fun loadSvgOrBitmap(url: String?, imageView: ImageView) {
            if (url.isNullOrEmpty()) {
                imageView.setImageResource(R.drawable.ic_image_placeholder)
                return
            }

            // Create an ImageLoader with SVG support
            val imageLoader = ImageLoader.Builder(imageView.context)
                .components {
                    add(SvgDecoder.Factory()) // Add SVG decoder
                }
                .build()

            // Load the image into the ImageView
            val request = ImageRequest.Builder(imageView.context)
                .data(url)
                .placeholder(R.drawable.ic_image_placeholder) // Placeholder while loading
                .error(R.drawable.ic_image_placeholder) // Error image if loading fails
                .target(imageView)
                .build()

            imageLoader.enqueue(request)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRankViewHolder {
        val binding = ItemTopRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopRankViewHolder(binding)
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: TopRankViewHolder, position: Int) {
        holder.bind()
    }
}
