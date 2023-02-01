package com.example.carpooling.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.carpooling.MainNavGraphDirections
import com.example.carpooling.R
import com.example.carpooling.data.model.User
import com.example.carpooling.data.restful.requests.UpdateProfileRequest
import com.example.carpooling.databinding.FragmentModifyProfileBinding
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class ModifyProfileFragment : Fragment() {

    private lateinit var binding: FragmentModifyProfileBinding
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val modifyUsername = binding.fieldModifyUsername
        val modifyBio = binding.fieldModifyBio

        modifyUsername.setText(userViewModel.user.value?.username ?: "")
        modifyBio.setText(userViewModel.user.value?.bio ?: "")

        binding.btnModify.setOnClickListener {
            val request = UpdateProfileRequest(
                username = modifyUsername.text.toString(),
                bio = modifyBio.text.toString()
            )
            userViewModel.updateProfile(request)
        }

        userViewModel.updateProfileResult.observe(viewLifecycleOwner) { success ->
            if (success.success) {
                val action = MainNavGraphDirections.toSuccessDialog(title = R.string.success_cancel_ride_title, message = R.string.success_cancel_ride_message)
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.profileFragment, false).build()
                navController.navigate(action, navOptions)
            }
        }
    }
}