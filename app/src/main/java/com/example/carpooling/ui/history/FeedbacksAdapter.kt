package com.example.carpooling.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carpooling.data.model.Feedback
import com.example.carpooling.databinding.FeedbackItemBinding

class FeedbacksAdapter(private val reviewer: Boolean) : ListAdapter<Feedback, FeedbacksAdapter.FeedbackViewHolder>(FeedbackDiffCallback) {

    private lateinit var binding: FeedbackItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        binding = FeedbackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedbackViewHolder(binding, reviewer)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val feedback = getItem(position)
        holder.bind(feedback)
    }

    class FeedbackViewHolder(private val binding: FeedbackItemBinding,
                             private val reviewer: Boolean
    ): RecyclerView.ViewHolder(binding.root) {
        private var currentFeedback: Feedback? = null

        fun bind(feedback: Feedback) {
            currentFeedback = feedback
            val picReference = if (reviewer) {
                binding.fieldItemFeedbackUsername.text = feedback.recipient.username
                feedback.recipient.profilePicReference
            }
            else {
                binding.fieldItemFeedbackUsername.text = feedback.reviewer.username
                feedback.reviewer.profilePicReference
            }
            binding.itemFeedbackText.text = feedback.text
            binding.itemFeedbackRating.rating = feedback.rating.toFloat()
            Glide.with(binding.root.context)
                .load("http://10.0.2.2:8080/carpooling_images/$picReference")
                .into(binding.imageItemFeedbackUser)
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