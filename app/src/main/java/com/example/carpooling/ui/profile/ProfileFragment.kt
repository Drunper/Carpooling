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
import com.bumptech.glide.Glide
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentProfileBinding
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        userViewModel.apply {
            getUserById(args.userId).observe(viewLifecycleOwner) { profileUser ->
                binding.profileBaseInfo.apply {
                    fieldProfileUsername.text = profileUser.username
                    fieldProfileEmail.text = profileUser.email
                }

                val imageView = binding.profileBaseInfo.imageProfilePic
                val picReference = profileUser.profilePicReference
                Glide.with(requireContext())
                    .load("http://10.0.2.2:8080/carpooling_images/$picReference")
                    .error(R.drawable.ic_user)
                    .into(imageView)
            }

            getUserStats(user.value!!.id).observe(viewLifecycleOwner) { userStats ->
                binding.profileBaseInfo.apply {
                    profileDriverRating.rating = userStats.driverRating
                    profilePassengerRating.rating = userStats.passengerRating
                    fieldProfileDriverRideCount.text = userStats.driverRideCount.toString()
                    fieldProfilePassengerRideCount.text = userStats.passengerRideCount.toString()
                    fieldProfileDriverFeedbackCount.text = userStats.driverFeedbackCount.toString()
                    fieldProfilePassengerFeedbackCount.text = userStats.passengerFeedbackCount.toString()
                }
            }
        }

        binding.btnFeedback.setOnClickListener {
            val action = ProfileFragmentDirections.toFeedback(userViewModel.user.value!!.id, reviewer = false)
            navController.navigate(action)
        }
    }
}