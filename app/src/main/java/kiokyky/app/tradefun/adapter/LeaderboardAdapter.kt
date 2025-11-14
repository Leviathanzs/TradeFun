package kiokyky.app.tradefun.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kiokyky.app.tradefun.databinding.ItemLeaderboardBinding
import kiokyky.app.tradefun.model.LeaderboardItem

class LeaderboardAdapter(private val items: List<LeaderboardItem>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LeaderboardItem) {
            binding.rankText.text = "#${item.rank}"
            binding.usernameText.text = item.username
            binding.assetText.text = item.asset
            binding.profitText.text = "${if (item.profitPercent >= 0) "+" else ""}${item.profitPercent}%"
            binding.profitText.setTextColor(
                binding.root.context.getColor(
                    if (item.profitPercent >= 0) android.R.color.holo_green_dark
                    else android.R.color.holo_red_dark
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLeaderboardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}