package com.example.carpooling.data

import com.example.carpooling.data.model.Error


class Result<T> private constructor(val status: Status, val data: T?, val error:Error?) {
    enum class Status {
        SUCCESS, ERROR
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }
        fun <T> error(error: Error?): Result<T> {
            return Result(Status.ERROR, null, error)
        }
    }
}