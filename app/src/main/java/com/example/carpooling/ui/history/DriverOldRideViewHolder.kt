package com.example.carpooling.ui.history

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carpooling.data.model.OldRide
import com.example.carpooling.databinding.DriverOldRideItemBinding
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.utils.formatCurrency
import java.text.NumberFormat
import java.util.*

class DriverOldRideViewHolder (
    private val binding: DriverOldRideItemBinding,
    val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var currentOldRide: OldRide? = null

    init {
        itemView.setOnClickListener {
            currentOldRide?.let {
                onClick(it.id)
            }
        }
    }

    fun bind(oldRide: OldRide) {
        currentOldRide = oldRide

        binding.fieldItemRideDepartureTime.text = oldRide.departureTime
        binding.fieldItemRideArrivalTime.text = oldRide.arrivalTime
        binding.fieldItemRidePrice.text = oldRide.price.formatCurrency(binding.root.context)
        val from = Geocoding.getAddressFromLatLng(
            binding.root.context,
            oldRide.from_lat,
            oldRide.from_lng
        )
        val to = Geocoding.getAddressFromLatLng(
            binding.root.context,
            oldRide.to_lat,
            oldRide.to_lng
        )
        binding.fieldItemRideFrom.text = from
        binding.fieldItemRideTo.text = to
    }
}