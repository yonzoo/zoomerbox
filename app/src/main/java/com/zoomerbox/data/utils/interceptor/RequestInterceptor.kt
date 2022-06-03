package com.zoomerbox.data.utils.interceptor

import android.content.SharedPreferences
import com.zoomerbox.model.util.PrefsKeys
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RequestInterceptor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = sharedPreferences.getString(PrefsKeys.TOKEN, null)
        if (token != null) {
            val requestBuilder =
                originalRequest.newBuilder().addHeader("X-Auth-Token", token).url(originalRequest.url)
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
        val requestBuilder = originalRequest.newBuilder().url(originalRequest.url)
        return chain.proceed(requestBuilder.build())
    }
}