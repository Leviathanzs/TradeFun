package kiokyky.app.tradefun.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kiokyky.app.tradefun.databinding.ItemCryptoBinding
import kiokyky.app.tradefun.model.CoinMarketItem

class CryptoListAdapter(
    private val onClick: (CoinMarketItem) -> Unit
) : ListAdapter<CoinMarketItem, CryptoListAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CoinMarketItem) {
            binding.cryptoName.text = item.name
            binding.cryptoSymbol.text = item.symbol.uppercase()
            binding.cryptoPrice.text = "$${item.current_price}"
            binding.cryptoLogo.load(item.image)
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CoinMarketItem>() {
        override fun areItemsTheSame(oldItem: CoinMarketItem, newItem: CoinMarketItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CoinMarketItem, newItem: CoinMarketItem): Boolean =
            oldItem == newItem
    }
}