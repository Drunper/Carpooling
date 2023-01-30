package com.example.carpooling.ui.myrides

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carpooling.databinding.MyRideTabItemBinding

class MyRidesTabAdapter(
    private val passengerActiveRidesAdapter: MyPassengerActiveRidesAdapter,
    private val driverActiveRidesAdapter: MyDriverActiveRidesAdapter
) : RecyclerView.Adapter<TabViewHolder>() {

    private lateinit var binding: MyRideTabItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        binding = MyRideTabItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        if (position == 0)
            holder.binding.recyclerView.adapter = passengerActiveRidesAdapter
        else
            holder.binding.recyclerView.adapter = driverActiveRidesAdapter
        holder.binding.recyclerView.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
    }

    override fun getItemCount(): Int {
        return 2
    }
}