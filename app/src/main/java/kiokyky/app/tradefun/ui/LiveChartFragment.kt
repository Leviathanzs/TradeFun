package kiokyky.app.tradefun.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import kiokyky.app.tradefun.R
import kiokyky.app.tradefun.api.ApiClient
import kiokyky.app.tradefun.databinding.FragmentLiveChartBinding
import kotlinx.coroutines.launch

class LiveChartFragment : Fragment() {

    private var _binding: FragmentLiveChartBinding? = null
    private val binding get() = _binding!!

    private var coinId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        coinId = arguments?.getString("coinId")
        binding.coinName.text = coinId?.replaceFirstChar { it.uppercase() } ?: "Unknown"

        loadCandlestickData()

        showTransactionFragment("history")

        binding.tabSwitcher.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.tabHistory -> showTransactionFragment("history")
                R.id.tabLive -> showTransactionFragment("live")
            }
        }
    }

    private fun loadCandlestickData() {
        lifecycleScope.launch {
            try {
                val ohlcData = ApiClient.service.getOhlcData(coinId ?: "", "usd", "1")
                val entries = ohlcData.mapIndexed { index, point ->
                    CandleEntry(
                        index.toFloat(),
                        point[2].toFloat(), // high
                        point[3].toFloat(), // low
                        point[1].toFloat(), // open
                        point[4].toFloat()  // close
                    )
                }

                val dataSet = CandleDataSet(entries, "Candlestick")
                dataSet.color = Color.BLUE
                dataSet.shadowColor = Color.DKGRAY
                dataSet.decreasingColor = Color.RED
                dataSet.increasingColor = Color.GREEN
                dataSet.neutralColor = Color.GRAY
                dataSet.setDrawValues(false)

                binding.candleChart.data = CandleData(dataSet)
                binding.candleChart.invalidate()
            } catch (e: Exception) {
                Log.e("LiveChartFragment", "Candlestick error", e)
                Toast.makeText(requireContext(), "Failed to load candlestick data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTransactionFragment(type: String) {
        val fragment = when (type) {
            "history" -> TransactionHistoryFragment()
            "live" -> LiveTransactionFragment()
            else -> Fragment()
        }
        childFragmentManager.beginTransaction()
            .replace(R.id.transactionContainer, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}