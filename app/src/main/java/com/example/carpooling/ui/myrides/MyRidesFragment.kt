package com.example.carpooling.ui.myrides

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
import com.example.carpooling.databinding.FragmentMyRidesBinding
import com.example.carpooling.ui.search.SearchResultFragmentDirections
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class MyRidesFragment : Fragment() {

    private lateinit var binding: FragmentMyRidesBinding
    private lateinit var adapters: List<MyRidesAdapter>
    private var currentRiderRidesPage: Int = 1
    private var currentParticipantsRidesPage: Int = 1
    private var totalRiderRidesPage: Int = 1
    private var totalParticipantsRidesPage: Int = 1

    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_rides, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val tabLayout = binding.tabLayoutMyRides
        val viewPager = binding.viewPagerMyRides
        adapters = listOf(
            MyRidesAdapter { rideID ->
                val action = MyRidesFragmentDirections.toParticipantRide(rideID)
                navController.navigate(action)
            }, MyRidesAdapter { rideID ->
                val action = MyRidesFragmentDirections.toRiderRide(rideID)
                navController.navigate(action)
            }
        )
        val scrollListeners = listOf(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        if (currentParticipantsRidesPage < totalParticipantsRidesPage) {
                            currentParticipantsRidesPage += 1
                            loadParticipantRides()
                        }
                    }
                }
            }, object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        if (currentRiderRidesPage < totalRiderRidesPage) {
                            currentRiderRidesPage += 1
                            loadRiderRides()
                        }
                    }
                }
            })

        val viewPagerAdapter = ParentAdapter(
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

        loadParticipantRides()
        loadRiderRides()
    }

    fun loadParticipantRides() {
        myRidesViewModel.getParticipantActiveRides(currentParticipantsRidesPage.toLong())
            .observe(viewLifecycleOwner) {
                adapters[0].submitList(it)
            }
    }

    fun loadRiderRides() {
        myRidesViewModel.getRiderActiveRides(currentRiderRidesPage.toLong())
            .observe(viewLifecycleOwner) {
                adapters[1].submitList(it)
            }
    }
}