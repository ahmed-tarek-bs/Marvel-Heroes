package com.example.marvelcharacters.core.network

import com.example.marvelcharacters.utils.generateMD5Hash
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelApi(
    private val baseURL: String,
    publicKey: String,
    privateKey: String
) {

    var retrofitClient: Retrofit

    private val apiKey: String
    private val hash: String
    private val timeStamp = System.currentTimeMillis().toString()

    init {
        retrofitClient = createClient()
        apiKey = publicKey
        hash = generateMD5Hash("$timeStamp$privateKey$publicKey")
    }

    private fun createClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL).client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
            .apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        okHttpClientBuilder.addInterceptor(loggingInterceptor)

        return okHttpClientBuilder
            .addInterceptor { getAuthInterceptor(it) }
            .build()
    }

    private fun getAuthInterceptor(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedUrl = originalRequest.url.newBuilder()
            .addQueryParameter("ts", timeStamp)
            .addQueryParameter("apikey", apiKey)
            .addQueryParameter("hash", hash)
            .build()

        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        return chain.proceed(modifiedRequest)
    }

}