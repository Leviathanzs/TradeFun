package kiokyky.app.tradefun.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kiokyky.app.tradefun.adapter.CryptoListAdapter
import kiokyky.app.tradefun.api.ApiClient
import kiokyky.app.tradefun.databinding.FragmentCryptoListBinding
import kiokyky.app.tradefun.model.CoinMarketItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CryptoListFragment : Fragment() {

    private var refreshJob: Job? = null
    private var _binding: FragmentCryptoListBinding? = null
    private val binding get() = _binding!!
    private lateinit var fullList: List<CoinMarketItem>
    private lateinit var adapter: CryptoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CryptoListAdapter { item ->
            val action = CryptoListFragmentDirections.actionCryptoListFragmentToLiveChartFragment(item.id)
            findNavController().navigate(action)
        }

        binding.cryptoRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.cryptoRecycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            retryWithDelay(
                maxRetries = 3,
                delayMillis = 5000,
                onError = { e ->
                    Log.e("CryptoListFragment", "Retry failed", e)
                    Toast.makeText(requireContext(), "Failed to fetch data after retries", Toast.LENGTH_LONG).show()
                }
            ) {
                fullList = ApiClient.service.getTopCoins()
                adapter.submitList(fullList)
                Log.d("CryptoListFragment", "Fetched ${fullList.size} top coins")
            }
        }

        // Filtering hanya aktif saat view lifecycle aktif
        binding.searchInput.addTextChangedListener { text ->
            val query = text?.toString()?.trim().orEmpty()
            val filtered = fullList.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.symbol.contains(query, ignoreCase = true)
            }
            adapter.submitList(filtered)
        }
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        refreshJob = viewLifecycleOwner.lifecycleScope.launch {
            while (isActive) {
                retryWithDelay(
                    maxRetries = 3,
                    delayMillis = 5000,
                    onError = { e ->
                        Log.e("CryptoListFragment", "Auto-refresh retry failed", e)
                    }
                ) {
                    val updatedList = ApiClient.service.getTopCoins()
                    if (updatedList != fullList) {
                        fullList = updatedList
                        adapter.submitList(fullList)
                        Log.d("CryptoListFragment", "Auto-refreshed ${fullList.size} coins")
                    }
                }
                delay(60000)
            }
        }
    }

    suspend fun retryWithDelay(
        maxRetries: Int = 3,
        delayMillis: Long = 3000,
        onError: (Throwable) -> Unit = {},
        block: suspend () -> Unit
    ) {
        var currentAttempt = 0
        while (currentAttempt < maxRetries) {
            try {
                block()
                return
            } catch (e: Exception) {
                currentAttempt++
                if (currentAttempt >= maxRetries) {
                    onError(e)
                    return
                }
                Log.w("Retry", "Attempt $currentAttempt failed, retrying in ${delayMillis}ms")
                delay(delayMillis)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}