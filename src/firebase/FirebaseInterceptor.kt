package com.example.firebase

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class FirebaseInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        with(chain.request()){
            url.newBuilder()
                .build().also { httpUrl ->
                    return chain
                        .proceed(
                            addHerders(this,httpUrl)
                        )
                }
        }
    }

    private fun addHerders(request: Request, httpUrl: HttpUrl) =
        request.newBuilder()
            .url(httpUrl)
            .addHeader("Authorization","key=${System.getenv("SERVER_KEY")}")
            .addHeader("Content-Type","application/json")
            .build()
}