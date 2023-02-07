package com.example.carpooling.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentUserProfileBinding
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        userViewModel.apply {
            user.observe(viewLifecycleOwner) { profileUser ->
                binding.profileBaseInfo.apply {
                    fieldProfileUsername.text = profileUser!!.username
                    fieldProfileEmail.text = profileUser.email
                    fieldProfileBio.text = profileUser.bio
                }
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

        val picReference = userViewModel.getProfilePicReference()
        val imageView = binding.profileBaseInfo.imageProfilePic

        Glide.with(this)
            .load("http://10.0.2.2:8080/carpooling_images/$picReference")
            .error(R.drawable.ic_user)
            .into(imageView)

        binding.btnReceivedFeedback.setOnClickListener {
            val action = UserProfileFragmentDirections.toUserFeedback(userViewModel.user.value!!.id, reviewer = false)
            navController.navigate(action)
        }

        binding.btnSentFeedback.setOnClickListener {
            val action = UserProfileFragmentDirections.toUserFeedback(userViewModel.user.value!!.id, reviewer = true)
            navController.navigate(action)
        }

        binding.btnModifyProfile.setOnClickListener {
            val action = UserProfileFragmentDirections.modifyProfile()
            navController.navigate(action)
        }
    }
}