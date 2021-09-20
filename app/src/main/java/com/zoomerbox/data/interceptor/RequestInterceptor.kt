package com.zoomerbox.data.interceptor

import com.zoomerbox.ZoomerboxApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

//class RequestInterceptor : Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val originalRequest = chain.request()
//
//        var token = ZoomerboxApplication.preferences.accessToken
//        if (token == null) {
//            token = ""
//        }
//        val requestBuilder = originalRequest.newBuilder().addHeader("token", token).url(originalRequest.url)
//        val request = requestBuilder.build()
//        return chain.proceed(request)
//    }
//}