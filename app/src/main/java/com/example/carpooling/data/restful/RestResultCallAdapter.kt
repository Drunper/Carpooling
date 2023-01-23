package com.example.carpooling.data.restful

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RestResultCallAdapter(
    private val resultType: Type
) : CallAdapter<Type, Call<RestResult<Type>>> {

    override fun responseType(): Type {
        return resultType
    }

    override fun adapt(call: Call<Type>): Call<RestResult<Type>> {
        return RestResultCall(call)
    }
}