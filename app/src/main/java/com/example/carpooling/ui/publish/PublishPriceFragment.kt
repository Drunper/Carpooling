package com.example.carpooling.ui.publish

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.preference.PreferenceManager
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentPublishPriceBinding
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import java.util.*

class PublishPriceFragment : Fragment() {

    private lateinit var binding: FragmentPublishPriceBinding
    private val publishViewModel: PublishViewModel by navGraphViewModels(R.id.publish_nav_graph){
        ViewModelFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish_price, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        publishViewModel.price.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.btnToPublishSummary.isEnabled = true
            }
        }

        binding.apply {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val currency = Currency.getInstance(sharedPreferences.getString("currency", "EUR")).symbol
            fieldPublishPrice.suffixText = currency

            btnToPublishSummary.setOnClickListener {
                val action = PublishPriceFragmentDirections.toPublishSummary()
                navController.navigate(action)
            }

            val afterTextChangedListener = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable) {
                    try {
                        val value = s.toString().toDouble()
                        publishViewModel.setPrice(value)
                    } catch (e: NumberFormatException) {
                        fieldPublishPrice.error = getString(R.string.error_publish_price)
                        publishViewModel.setPrice(null)
                    }
                }
            }

            fieldPublishPrice.editText!!.addTextChangedListener(afterTextChangedListener)
        }
    }
}