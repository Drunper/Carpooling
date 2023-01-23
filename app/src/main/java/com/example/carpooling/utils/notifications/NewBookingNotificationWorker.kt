package com.example.carpooling.utils.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.carpooling.data.restful.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewBookingNotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            val result = ApiClient.getApiService()
        }
        return Result.success()
    }

}