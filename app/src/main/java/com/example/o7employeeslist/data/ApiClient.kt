package com.example.o7employeeslist.data

import com.example.o7employeeslist.model.GoogleSearchListModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


interface ServicesApiInterface {
    @GET("v1")
    fun getProfileInformation(@Query("q") name: String,
                              @Query("key") key: String = ApiModule.API_KEY,
                              @Query("cx") cx: String = ApiModule.GOOGLE_CX,
                              @Query("num") num: Int = 5): Call<GoogleSearchListModel>
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    const val API_KEY = "AIzaSyBdxrw-c6TrtqmQF0BtJoLWUr5ybykvGx4"
    const val GOOGLE_CX = "7bd85f61c9117ac8d"
    private const val API_BASE_URL = "https://customsearch.googleapis.com/customsearch/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(API_BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ServicesApiInterface =
        retrofit.create(ServicesApiInterface::class.java)

//    @Singleton
//    @Provides
//    fun providesRepository(apiService: ServicesApiInterface) = Repository(apiService)
}
