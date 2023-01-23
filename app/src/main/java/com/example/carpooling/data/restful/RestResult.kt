package com.example.carpooling.data.restful

sealed interface RestResult<T : Any>

class RestSuccess<T : Any>(val data: T) : RestResult<T>
class RestError<T : Any>(val statusCode: Int, val message: String?) : RestResult<T>
class RestException<T : Any>(val exception: Throwable) : RestResult<T>
