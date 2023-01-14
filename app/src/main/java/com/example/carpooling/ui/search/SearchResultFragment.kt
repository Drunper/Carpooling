package com.example.carpooling.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentSearchResultBinding
import com.example.carpooling.viewmodels.ActiveRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding

    private val activeRidesViewModel: ActiveRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding: FragmentSearchResultBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_search_result, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val adapter =
            ActiveRidesAdapter { activeRideID ->
                val action = SearchResultFragmentDirections.goToActiveRide(activeRideID)
                navController.navigate(action)
            }

        binding.recyclerView.adapter = adapter
        activeRidesViewModel.activeRides.observe(viewLifecycleOwner) { activeRides ->
            adapter.submitList(activeRides)
        }
    }
}