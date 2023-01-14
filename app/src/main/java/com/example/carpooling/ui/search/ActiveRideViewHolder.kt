package com.example.carpooling.ui.search

import android.location.Geocoder
import androidx.recyclerview.widget.RecyclerView
import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.databinding.ActiveRideItemBinding
import java.util.*

class ActiveRideViewHolder(
    private val binding: ActiveRideItemBinding,
    val onClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root)  {

    private var currentActiveRide: ActiveRide? = null

    init {
        itemView.setOnClickListener {
            currentActiveRide?.let {
                onClick(it.id)
            }
        }
    }

    fun bind(activeRide: ActiveRide) {
        currentActiveRide = activeRide
        binding.textViewTime.text = activeRide.time
        binding.textViewDate.text = activeRide.date
        val geocoder = Geocoder(binding.root.context, Locale.getDefault())
        val fromAddress =
            geocoder.getFromLocation(activeRide.from_lat, activeRide.from_lng, 1)[0]
        val fromAddressString = "${fromAddress.locality}, ${fromAddress.adminArea}"
        val toAddress =
            geocoder.getFromLocation(activeRide.to_lat, activeRide.to_lng, 1)[0]
        val toAddressString = "${toAddress.locality}, ${toAddress.adminArea}"
        binding.textViewFrom.text = fromAddressString
        binding.textViewTo.text = toAddressString
    }
}