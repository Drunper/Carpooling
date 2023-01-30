package com.example.carpooling.ui.activeride

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carpooling.data.model.User
import com.example.carpooling.databinding.PassengerItemBinding

class PassengerAdapter: ListAdapter<User, PassengerViewHolder>(PassengerDiffCallback) {

    private lateinit var binding: PassengerItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        binding = PassengerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PassengerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        val activeRide = getItem(position)
        holder.bind(activeRide)
    }
}


class PassengerViewHolder(private val binding: PassengerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private var currentPassenger: User? = null

    fun bind(passenger: User) {
        currentPassenger = passenger
        binding.fieldRidePassengerName.text = passenger.username
        val picReference = passenger.profilePicReference
        Glide.with(binding.root.context).load("http://10.0.2.2:8080/carpooling_images/$picReference").into(binding.imageRidePassenger)
    }
}

object PassengerDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
}