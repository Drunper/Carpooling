package com.example.carpooling.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.carpooling.data.model.OldRide
import com.example.carpooling.databinding.OldRideItemBinding

class MyOldRidesAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<OldRide, OldRideViewHolder>(OldRideDiffCallback) {

    private lateinit var binding: OldRideItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OldRideViewHolder {
        binding = OldRideItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OldRideViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: OldRideViewHolder, position: Int) {
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