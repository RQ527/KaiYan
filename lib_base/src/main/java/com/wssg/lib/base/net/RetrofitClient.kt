package com.wssg.lib.base.net

import android.util.Log
import com.wssg.lib.base.utils.achieveValue
import com.wssg.lib.base.utils.saveValue
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/6/23
 * @Description:
 */
object RetrofitClient {

    private var commonOkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private var saveCookieOkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain: Interceptor.Chain ->
            val response = chain.proceed(chain.request())
            if (response.headers("Set-Cookie").isNotEmpty()) {
                val sb = StringBuilder()
                for (i in response.headers("Set-Cookie")) {
                    sb.append(i).append(";")
                }
                saveValue("cookie",sb.substring(0,sb.length-1))
            }
            Log.d("RQ", "saveCookie")
            return@addInterceptor response
        }
        .build()

    private var putCookietOkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor{chain ->
            val request = chain.request().newBuilder().apply {
                val cookie = achieveValue("cookie")
                if (cookie != null) {
                    addHeader("Cookie", cookie)
                }
                Log.d("RQ", "$cookie")
            }.build()
            return@addInterceptor chain.proceed(request)
        }
        .build()

    private var retrofit = Retrofit.Builder()
        .baseUrl("http://baobab.kaiyanapp.com")
        .addConverterFactory(GsonConverterFactory.create())

    fun <T> getService(
        serviceClass: Class<T>,
        isNeedCookie: Boolean = false,
        isNeedInterCookie: Boolean = false
    ): T {
        if (isNeedCookie) return retrofit.client(putCookietOkHttpClient).build().create(serviceClass)
        if (isNeedInterCookie) return retrofit.client(saveCookieOkHttpClient).build().create(serviceClass)
        return retrofit.client(commonOkHttpClient).build().create(serviceClass)
    }
}