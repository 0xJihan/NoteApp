package com.jihan.noteapp.retrofit

import com.jihan.noteapp.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor() : Interceptor {
    @Inject
    lateinit var tokenManager: TokenManager
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer ${tokenManager.getToken()}")
        return chain.proceed(request.build())
    }
}