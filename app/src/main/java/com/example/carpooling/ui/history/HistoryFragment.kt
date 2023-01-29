package com.example.carpooling.ui.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.carpooling.R
import com.example.carpooling.data.model.OldRide
import com.example.carpooling.databinding.FragmentHistoryBinding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapters: List<MyOldRidesAdapter>

    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("historyLifecycle", "onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val tabLayout = binding.tabLayoutHistory
        val viewPager = binding.viewPagerHistory
        Log.d("historyLifecycle", "onViewCreated")
        adapters = listOf(
            MyOldRidesAdapter { rideID ->
                val action = HistoryFragmentDirections.toPassengerOldRide(rideID)
                navController.navigate(action)
            }, MyOldRidesAdapter { rideID ->
                val action = HistoryFragmentDirections.toDriverOldRide(rideID)
                navController.navigate(action)
            }
        )

        val viewPagerAdapter = HistoryTabAdapter(
            adapters = adapters
        )
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            viewPager.setCurrentItem(tab.position, true)
            if (position == 1)
                tab.text = "Pubblicati"
            if (position == 0)
                tab.text = "Prenotati"
        }.attach()

        myRidesViewModel.getPassengerOldRides().observe(viewLifecycleOwner) { rides ->
            adapters[0].submitList(rides)
        }

        myRidesViewModel.getDriverOldRides().observe(viewLifecycleOwner) { rides ->
            adapters[1].submitList(rides)
        }
    }
}