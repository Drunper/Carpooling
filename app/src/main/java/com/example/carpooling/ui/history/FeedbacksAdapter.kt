package com.example.carpooling.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carpooling.data.model.Feedback
import com.example.carpooling.databinding.FeedbackItemBinding

class FeedbacksAdapter(private val onClick: (Long) -> Unit) : ListAdapter<Feedback, FeedbacksAdapter.FeedbackViewHolder>(FeedbackDiffCallback) {

    private lateinit var binding: FeedbackItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        binding = FeedbackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedbackViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val feedback = getItem(position)
        holder.bind(feedback)
    }

    class FeedbackViewHolder(private val binding: FeedbackItemBinding,
                             private val onClick: (Long) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        private var feedback: Feedback? = null

        init {
            itemView.setOnClickListener {
                feedback?.let {
                    onClick(it.id)
                }
            }
        }

        fun bind(value: Feedback) {
            feedback = value
            binding.fieldText.text = value.text
            binding.fieldUsername.text = value.reviewer.username
            binding.fieldRating.text = value.rating.toString()
        }
    }
}

object FeedbackDiffCallback : DiffUtil.ItemCallback<Feedback>() {
    override fun areItemsTheSame(oldItem: Feedback, newItem: Feedback): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Feedback, newItem: Feedback): Boolean {
        return oldItem.id == newItem.id
    }
}