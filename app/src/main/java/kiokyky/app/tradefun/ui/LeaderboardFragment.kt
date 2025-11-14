package kiokyky.app.tradefun.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kiokyky.app.tradefun.adapter.LeaderboardAdapter
import kiokyky.app.tradefun.databinding.FragmentLeaderboardBinding
import kiokyky.app.tradefun.model.LeaderboardItem

class LeaderboardFragment : Fragment() {

    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!

    private val dummyData = listOf(
        LeaderboardItem(1, "Oky", "BTC", 12.5),
        LeaderboardItem(2, "Rina", "ETH", 9.3),
        LeaderboardItem(3, "Budi", "IHSG", -2.1),
        LeaderboardItem(4, "Sari", "S&P 500", 5.7),
        LeaderboardItem(5, "Dika", "BTC", 0.0)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.leaderboardRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.leaderboardRecycler.adapter = LeaderboardAdapter(dummyData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}