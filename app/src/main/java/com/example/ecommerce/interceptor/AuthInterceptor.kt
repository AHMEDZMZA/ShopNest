package com.example.ecommerce.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(var authToken:String?=null,var languageCode: String="en" ) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        authToken?.let {
            requestBuilder.addHeader("Authorization", it)
        }
        requestBuilder.addHeader("lang", languageCode)
        return chain.proceed(requestBuilder.build())
    }

    fun setToken(token:String){
        authToken = token
    }
    fun setLanguage(languageCode: String) {
        this.languageCode = languageCode
        }


}
