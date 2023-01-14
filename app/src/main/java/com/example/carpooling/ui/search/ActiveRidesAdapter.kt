package com.example.carpooling.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.databinding.ActiveRideItemBinding

class ActiveRidesAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<ActiveRide, ActiveRideViewHolder>(ActiveRideDiffCallback) {

    private lateinit var binding: ActiveRideItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveRideViewHolder {
        binding = ActiveRideItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActiveRideViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ActiveRideViewHolder, position: Int) {
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