package com.example.carpooling.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpooling.MainNavGraphDirections
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentProfileFeedbackBinding
import com.example.carpooling.ui.history.FeedbacksAdapter
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFeedbackFragment : Fragment() {
    private lateinit var binding: FragmentProfileFeedbackBinding
    private lateinit var passengerFeedbacksAdapter: FeedbacksAdapter
    private lateinit var driverFeedbacksAdapter: FeedbacksAdapter
    private val args: ProfileFeedbackFragmentArgs by navArgs()
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_feedback, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        val tabLayout = binding.tabLayoutFeedback
        val viewPager = binding.viewPagerFeedback

        passengerFeedbacksAdapter =
            FeedbacksAdapter(reviewer = args.reviewer, onClick = { userId ->
                val action = MainNavGraphDirections.toProfile(userId)
                navController.navigate(action)
            })

        driverFeedbacksAdapter =
            FeedbacksAdapter(reviewer = args.reviewer, onClick = { userId ->
                val action = MainNavGraphDirections.toProfile(userId)
                navController.navigate(action)
            })

        val viewPagerAdapter = FeedbackTabAdapter(passengerFeedbacksAdapter, driverFeedbacksAdapter)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            viewPager.setCurrentItem(tab.position, true)
            if (position == 0)
                tab.text = getString(R.string.tab_title_passenger)
            if (position == 1)
                tab.text = getString(R.string.tab_title_driver)
        }.attach()

        if (args.reviewer) {
            userViewModel.getPassengerSentFeedback(args.userId).observe(viewLifecycleOwner) {
                passengerFeedbacksAdapter.submitList(it)
            }
            userViewModel.getDriverSentFeedback(args.userId).observe(viewLifecycleOwner) {
                driverFeedbacksAdapter.submitList(it)
            }
        } else {
            userViewModel.getPassengerReceivedFeedback(args.userId).observe(viewLifecycleOwner) {
                passengerFeedbacksAdapter.submitList(it)
            }
            userViewModel.getDriverReceivedFeedback(args.userId).observe(viewLifecycleOwner) {
                driverFeedbacksAdapter.submitList(it)
            }
        }
    }
}