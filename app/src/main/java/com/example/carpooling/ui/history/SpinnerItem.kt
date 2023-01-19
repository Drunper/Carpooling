package com.example.carpooling.ui.history

data class SpinnerItem(
    val id: Long,
    val username: String
) {
    override fun toString(): String {
        return username
    }
}

