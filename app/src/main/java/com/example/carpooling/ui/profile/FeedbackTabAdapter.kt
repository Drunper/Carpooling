package com.example.carpooling.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carpooling.databinding.FeedbackTabItemBinding
import com.example.carpooling.ui.history.FeedbacksAdapter

class FeedbackTabAdapter (
    private val passengerFeedbacksAdapter: FeedbacksAdapter,
    private val driverFeedbacksAdapter: FeedbacksAdapter
)
    : RecyclerView.Adapter<FeedbackTabAdapter.TabViewHolder>() {

    private lateinit var binding: FeedbackTabItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        binding = FeedbackTabItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        if (position == 0)
            holder.binding.recyclerView.adapter = passengerFeedbacksAdapter
        else
            holder.binding.recyclerView.adapter = driverFeedbacksAdapter
        holder.binding.recyclerView.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
    }

    override fun getItemCount(): Int {
        return 2
    }

    class TabViewHolder(val binding: FeedbackTabItemBinding) : RecyclerView.ViewHolder(binding.root)
}