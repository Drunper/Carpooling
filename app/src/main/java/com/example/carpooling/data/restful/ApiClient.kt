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

    fun getLoginApiService(): ApiServiceInterface {
        return createApiService()
    }

    fun setApiService(token: String) {
        if (apiService == null) {
            apiService = createApiService(token)
        }
    }

    fun resetApiService() {
        apiService = null
    }

    private fun createApiService(): ApiServiceInterface {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RestResultCallAdapterFactory.create())
            .client(mOkHttpClient)
            .build()
        return retrofit.create(ApiServiceInterface::class.java)
    }

    private fun createApiService(token: String) : ApiServiceInterface {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val basicAuthInterceptor = BasicAuthInterceptor(token, "")
        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor(basicAuthInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RestResultCallAdapterFactory.create())
            .client(mOkHttpClient)
            .build()
        return retrofit.create(ApiServiceInterface::class.java)
    }
}