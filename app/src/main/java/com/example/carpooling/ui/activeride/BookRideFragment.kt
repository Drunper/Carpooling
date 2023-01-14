package com.example.carpooling.ui.activeride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentBookRideBinding
import com.example.carpooling.viewmodels.BookingViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class BookRideFragment : Fragment() {

    private lateinit var binding: FragmentBookRideBinding
    private val args: BookRideFragmentArgs by navArgs()

    private val bookingViewModel: BookingViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding: FragmentBookRideBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_ride, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        bookingViewModel.bookRideResult.observe(viewLifecycleOwner) { success ->
            if(success) {
                val action = BookRideFragmentDirections.successBooking()
                navController.navigate(action)
            }
        }

        val btnBook = binding.btnBookRideBook
        btnBook.setOnClickListener {
            bookingViewModel.bookRide(args.activeRideId)
        }
    }
}