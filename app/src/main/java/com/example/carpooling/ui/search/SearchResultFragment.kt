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
import com.example.carpooling.ui.myrides.MyPassengerActiveRidesAdapter
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.utils.getString
import com.example.carpooling.viewmodels.SearchViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private val searchViewModel: SearchViewModel by activityViewModels {
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
            MyPassengerActiveRidesAdapter { activeRideID ->
                val action = SearchResultFragmentDirections.goToActiveRide(activeRideID)
                navController.navigate(action)
            }

        searchViewModel.resetBookRideResult()

        searchViewModel.searchQuery.value!!.let {
            binding.fieldSearchQueryDatetime.text = getString(R.string.field_search_query_datetime_format, it.date, it.time)
        }

        searchViewModel.from.observe(viewLifecycleOwner) { from ->
            binding.fieldSearchQueryFrom.text = from.getString()
        }

        searchViewModel.to.observe(viewLifecycleOwner) { to ->
            binding.fieldSearchQueryTo.text = to.getString()
        }

        binding.recyclerView.adapter = adapter
        searchViewModel.executeQuery().observe(viewLifecycleOwner) { rides ->
            if (rides.isEmpty()) {
                binding.labelSearchResultsEmpty.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.labelSearchResultsEmpty.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(rides)
            }
        }
    }
}