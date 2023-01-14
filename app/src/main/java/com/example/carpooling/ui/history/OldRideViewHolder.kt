package com.example.carpooling.ui.history

import androidx.recyclerview.widget.RecyclerView
import com.example.carpooling.data.model.OldRide
import com.example.carpooling.databinding.OldRideItemBinding
import com.example.carpooling.utils.Geocoding

class OldRideViewHolder (
    private val binding: OldRideItemBinding,
    val onClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root)  {

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
            binding.textViewTime.text = oldRide.time
            binding.textViewDate.text = oldRide.date
            val fromAddressString = Geocoding.getAddressFromLatLng(binding.root.context, oldRide.from_lat, oldRide.from_lng)
            val toAddressString = Geocoding.getAddressFromLatLng(binding.root.context, oldRide.to_lat, oldRide.to_lng)
            binding.textViewFrom.text = fromAddressString
            binding.textViewTo.text = toAddressString
        }
    }