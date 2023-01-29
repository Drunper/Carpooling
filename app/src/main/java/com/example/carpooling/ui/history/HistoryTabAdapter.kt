package com.example.carpooling.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carpooling.databinding.MyRideTabItemBinding

class HistoryTabAdapter(private val adapters: List<MyOldRidesAdapter>)
: RecyclerView.Adapter<HistoryTabAdapter.TabViewHolder>() {

    private lateinit var binding: MyRideTabItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        binding = MyRideTabItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.binding.recyclerView.adapter = adapters[position]
        holder.binding.recyclerView.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
    }

    override fun getItemCount(): Int {
        return adapters.size
    }

    class TabViewHolder(val binding: MyRideTabItemBinding) : RecyclerView.ViewHolder(binding.root) {}
}
