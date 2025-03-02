package com.ankn.cryptocurrencyminiapp.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.ankn.cryptocurrencyminiapp.R
import com.ankn.cryptocurrencyminiapp.databinding.ItemCoinBinding
import com.ankn.cryptocurrencyminiapp.databinding.ItemInviteBinding
import com.ankn.cryptocurrencyminiapp.domain.model.Coin
import com.ankn.cryptocurrencyminiapp.presentation.utils.extensions.setColoredText
import java.text.DecimalFormat
import kotlin.math.absoluteValue

class OtherCoinsAdapter(private val onItemClick: (Coin) -> Unit) : PagingDataAdapter<Coin, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean = oldItem.uuid == newItem.uuid
            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean = oldItem == newItem
        }

        private const val VIEW_TYPE_COIN = 0
        private const val VIEW_TYPE_INVITE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (isInvitePosition(position)) VIEW_TYPE_INVITE else VIEW_TYPE_COIN
    }

    private fun isInvitePosition(position: Int): Boolean {
        val pos = position + 1
        if (pos % 5 != 0) return false
        val factor = pos / 5
        // ตรวจสอบว่า factor เป็นเลขยกกำลังของ 2 หรือไม่
        return factor > 0 && (factor and (factor - 1)) == 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COIN -> {
                val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CoinViewHolder(binding)
            }
            VIEW_TYPE_INVITE -> {
                val binding = ItemInviteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InviteViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoinViewHolder -> {
                val coin = getItem(position) ?: return
                holder.bind(coin)
            }
            is InviteViewHolder -> holder.bind()
        }
    }

    inner class CoinViewHolder(private val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(coin: Coin) {

            loadSvgOrBitmap(coin.iconUrl, binding.imgCoin)

            binding.tvCoinName.text = coin.name
            binding.tvCoinSymbol.text = coin.symbol
            binding.tvCoinPrice.text = "$${coin.price.replace(", ","").changToCurrencyDecimalFormat()}"

            val changeText = coin.change?.toDoubleOrNull()?.let {
                "${it.absoluteValue}%"
            } ?: "N/A"

            binding.tvChange.text = changeText

            val isUp = (coin.change?.toDoubleOrNull() ?: 0.0) >= 0.0
            binding.ivArrow.setImageResource(if (isUp) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
            binding.tvChange.setTextColor(
                ContextCompat.getColor(itemView.context, if (isUp) R.color.green else R.color.red)
            )

            binding.root.setOnClickListener {
                onItemClick(coin)
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

        private fun String.changToCurrencyDecimalFormat(): String {
            if (this == "-" || this == "." || this == "0" || this == ".00" || this.isEmpty()) {
                return "0.00"
            }
            val number = java.lang.Double.parseDouble(this.replace(",", ""))
            val format = DecimalFormat("###,###,###,###,###.#####")

            format.isDecimalSeparatorAlwaysShown = true
            format.minimumFractionDigits = 2
            return format.format(number)
        }
    }

    inner class InviteViewHolder(private val binding: ItemInviteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.inviteText.setColoredText(
                fullText = binding.root.context.getString(R.string.text_invite_friend),
                targetText = "Invite your friend",
                primaryColor = ContextCompat.getColor(binding.root.context, R.color.black),
                targetColor = ContextCompat.getColor(binding.root.context, R.color.brilliant_azure),
                boldTarget = true
            )
        }
    }
}