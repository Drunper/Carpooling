package com.example.carpooling.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.carpooling.data.model.OldRide
import com.example.carpooling.databinding.DriverOldRideItemBinding
import com.example.carpooling.databinding.PassengerOldRideItemBinding

class MyDriverOldRidesAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<OldRide, DriverOldRideViewHolder>(OldRideDiffCallback) {

    private lateinit var binding: DriverOldRideItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverOldRideViewHolder {
        binding = DriverOldRideItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriverOldRideViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: DriverOldRideViewHolder, position: Int) {
        val oldRide = getItem(position)
        holder.bind(oldRide)
    }
}

class MyPassengerOldRidesAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<OldRide, PassengerOldRideViewHolder>(OldRideDiffCallback) {

    private lateinit var binding: PassengerOldRideItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerOldRideViewHolder {
        binding = PassengerOldRideItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PassengerOldRideViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: PassengerOldRideViewHolder, position: Int) {
        val oldRide = getItem(position)
        holder.bind(oldRide)
    }
}

object OldRideDiffCallback : DiffUtil.ItemCallback<OldRide>() {
    override fun areItemsTheSame(oldItem: OldRide, newItem: OldRide): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: OldRide, newItem: OldRide): Boolean {
        return oldItem.id == newItem.id
    }
}