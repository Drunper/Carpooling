package com.example.carpooling.ui.myrides

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.databinding.DriverActiveRideItemBinding
import com.example.carpooling.databinding.PassengerActiveRideItemBinding

class MyPassengerActiveRidesAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<ActiveRide, PassengerActiveRideViewHolder>(ActiveRideDiffCallback) {

    private lateinit var binding: PassengerActiveRideItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerActiveRideViewHolder {
        binding = PassengerActiveRideItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PassengerActiveRideViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: PassengerActiveRideViewHolder, position: Int) {
        val activeRide = getItem(position)
        holder.bind(activeRide)
    }
}

class MyDriverActiveRidesAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<ActiveRide, DriverActiveRideViewHolder>(ActiveRideDiffCallback) {

    private lateinit var binding: DriverActiveRideItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverActiveRideViewHolder {
        binding = DriverActiveRideItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriverActiveRideViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: DriverActiveRideViewHolder, position: Int) {
        val activeRide = getItem(position)
        holder.bind(activeRide)
    }
}

object ActiveRideDiffCallback : DiffUtil.ItemCallback<ActiveRide>() {
    override fun areItemsTheSame(oldItem: ActiveRide, newItem: ActiveRide): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ActiveRide, newItem: ActiveRide): Boolean {
        return oldItem.id == newItem.id
    }
}
