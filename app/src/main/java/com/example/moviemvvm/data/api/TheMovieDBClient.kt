package com.example.moviemvvm.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


const val API_KEY ="c2282707c91ae76636ea6632e368f112"

const val BASE_URL ="https://api.themoviedb.org/3/"

const val POSTER_BASE_URL ="https://image.tmdb.org/t/p/w342"

const val FIRST_PAGE = 1
const val POST_PER_PAGE =20


//https://api.themoviedb.org/3/movie/popular?api_key=c2282707c91ae76636ea6632e368f112
//https://api.themoviedb.org/3/movie/454626?api_key=c2282707c91ae76636ea6632e368f112
//https://image.tmdb.org/t/p/w342/s8qRIwA0zDPbnRekeU0rDwWE7q7.jpg

object TheMovieDBClient {
    fun getClient(): TheMovieDBInterface {

        val requestInterceptor = Interceptor { chain ->
            //

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60,TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)

    }


}