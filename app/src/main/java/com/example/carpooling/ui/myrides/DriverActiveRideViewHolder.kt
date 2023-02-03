package com.example.carpooling.ui.myrides

import androidx.recyclerview.widget.RecyclerView
import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.databinding.DriverActiveRideItemBinding
import com.example.carpooling.utils.Geocoding
import java.text.NumberFormat
import java.util.*

class DriverActiveRideViewHolder (
    private val binding: DriverActiveRideItemBinding,
    val onClick: (Int) -> Unit
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

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 2
        format.currency = Currency.getInstance("EUR") // TODO: bisogna usare la currency utilizzata di default dal sistema

        binding.fieldItemRideDepartureTime.text = activeRide.departureTime
        binding.fieldItemRideArrivalTime.text = activeRide.arrivalTime
        binding.fieldItemRidePrice.text = format.format(activeRide.price)

        val from = Geocoding.getAddressFromLatLng(binding.root.context, activeRide.from_lat,  activeRide.from_lng)
        val to = Geocoding.getAddressFromLatLng(binding.root.context, activeRide.to_lat, activeRide.to_lng)

        binding.fieldItemRideFrom.text = from
        binding.fieldItemRideTo.text = to
    }
}