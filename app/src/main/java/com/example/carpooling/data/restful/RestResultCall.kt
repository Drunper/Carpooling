package com.example.carpooling.data.restful

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class RestResultCall<T : Any>(private val delegate: Call<T>) : Call<RestResult<T>> {

    override fun enqueue(callback: Callback<RestResult<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val restResult = if (response.isSuccessful && body != null) {
                    RestSuccess(body)
                } else {
                    RestError(statusCode = response.code(), message = response.message())
                }
                callback.onResponse(this@RestResultCall, Response.success(restResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val restResult = if (t is HttpException) {
                    RestError(statusCode = t.code(), message = t.message())
                } else {
                    RestException<T>(t)
                }
                callback.onResponse(this@RestResultCall, Response.success(restResult))
            }
        })
    }

    override fun execute(): Response<RestResult<T>> {
        throw NotImplementedError()
    }

    override fun clone(): Call<RestResult<T>> {
        return RestResultCall(delegate.clone())
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun cancel() {
        delegate.cancel()
    }
}