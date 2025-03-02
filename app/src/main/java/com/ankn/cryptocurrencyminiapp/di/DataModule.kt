package com.ankn.cryptocurrencyminiapp.di

import com.ankn.cryptocurrencyminiapp.BuildConfig
import com.ankn.cryptocurrencyminiapp.data.datasource.remote.CoinApiService
import com.ankn.cryptocurrencyminiapp.data.datasource.source.CoinPagingSource
import com.ankn.cryptocurrencyminiapp.data.repository.CoinRepositoryImpl
import com.ankn.cryptocurrencyminiapp.domain.repository.CoinRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single { provideRetrofit() }
    single { get<Retrofit>().create(CoinApiService::class.java) }
    factory { CoinPagingSource(get(), false) }
    single <CoinRepository> { CoinRepositoryImpl(get()) }
}

fun provideRetrofit(): Retrofit {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor(apiKey))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

val apiKey = "coinranking1366a924da735c8107dfa549795bac5b9a42eb18323f944e"

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-access-token", apiKey)
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}
