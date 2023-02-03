package com.example.carpooling.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.data.restful.requests.UpdateProfileRequest
import com.example.carpooling.databinding.FragmentModifyProfileBinding
import com.example.carpooling.utils.showSnackbar
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

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

        modifyUsername.editText!!.setText(userViewModel.user.value?.username ?: "")
        modifyBio.editText!!.setText(userViewModel.user.value?.bio ?: "")

        binding.btnModify.setOnClickListener {
            val request = UpdateProfileRequest(
                username = modifyUsername.editText!!.text.toString(),
                bio = modifyBio.editText!!.text.toString()
            )
            userViewModel.updateProfile(request).observe(viewLifecycleOwner) { success ->
                if (success) {
                    binding.root.showSnackbar(
                        requireView(),
                        getString(R.string.snackbar_update_profile_success),
                        Snackbar.LENGTH_LONG,
                        null,
                    ) {}
                    navController.popBackStack()
                }
            }
        }
    }
}