package com.example.carpooling.data.restful

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var apiService: ApiServiceInterface? = null

    fun getApiService(): ApiServiceInterface {
        return apiService!!
    }

    fun getLoginApiService(email: String, password: String): ApiServiceInterface {
        return createApiService(email, password)
    }

    fun setApiService(email: String, password: String) {
        if (apiService == null) {
            apiService = createApiService(email, password)
        }
    }

    fun resetApiService() {
        apiService = null
    }

    private fun createApiService(email: String, password: String): ApiServiceInterface {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val basicAuthInterceptor = BasicAuthInterceptor(email, password)

        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor(basicAuthInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        return retrofit.create(ApiServiceInterface::class.java)
    }
}