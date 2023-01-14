package com.example.carpooling.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentHistoryBinding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapters: List<MyOldRidesAdapter>
    private var currentDriverRidesPage: Int = 1
    private var currentPassengerRidesPage: Int = 1
    private var totalDriverRidesPage: Int = 1
    private var totalPassengerRidesPage: Int = 1

    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val tabLayout = binding.tabLayoutHistory
        val viewPager = binding.viewPagerHistory
        adapters = listOf(
            MyOldRidesAdapter { rideID ->
                val action = HistoryFragmentDirections.toPassengerOldRide(rideID)
                navController.navigate(action)
            }, MyOldRidesAdapter { rideID ->
                val action = HistoryFragmentDirections.toDriverOldRide(rideID)
                navController.navigate(action)
            }
        )
        val scrollListeners = listOf(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        if (currentPassengerRidesPage < totalPassengerRidesPage) {
                            currentPassengerRidesPage += 1
                            loadPassengerRides()
                        }
                    }
                }
            }, object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        if (currentDriverRidesPage < totalDriverRidesPage) {
                            currentDriverRidesPage += 1
                            loadDriverRides()
                        }
                    }
                }
            })

        val viewPagerAdapter = TabAdapter(
            scrollListeners = scrollListeners,
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

        loadPassengerRides()
        loadDriverRides()
    }


    fun loadPassengerRides() {
        myRidesViewModel.getParticipantOldRides(currentPassengerRidesPage.toLong())
            .observe(viewLifecycleOwner) {
                adapters[0].submitList(it)
            }
    }

    fun loadDriverRides() {
        myRidesViewModel.getRiderOldRides(currentDriverRidesPage.toLong())
            .observe(viewLifecycleOwner) {
                adapters[1].submitList(it)
            }
    }
}