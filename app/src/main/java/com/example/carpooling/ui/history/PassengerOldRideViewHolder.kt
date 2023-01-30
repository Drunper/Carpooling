package com.example.carpooling.ui.history

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carpooling.data.model.OldRide
import com.example.carpooling.databinding.PassengerOldRideItemBinding
import com.example.carpooling.utils.Geocoding
import java.text.NumberFormat
import java.util.*

class PassengerOldRideViewHolder(
    private val binding: PassengerOldRideItemBinding,
    val onClick: (Long) -> Unit
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
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 2
        format.currency =
            Currency.getInstance("EUR") // TODO: bisogna usare la currency utilizzata di default dal sistema

        binding.fieldItemRideDepartureTime.text = oldRide.departureTime
        binding.fieldItemRideArrivalTime.text = oldRide.arrivalTime
        binding.fieldItemRidePrice.text = format.format(oldRide.price)
        binding.fieldItemRideRiderName.text = oldRide.rider.username

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

        val picReference = oldRide.rider.profilePicReference

        Glide.with(binding.root.context)
            .load("http://10.0.2.2:8080/carpooling_images/$picReference")
            .into(binding.imageItemRideRider)
    }
}