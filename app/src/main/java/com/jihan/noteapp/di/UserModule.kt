package com.jihan.noteapp.di

import com.jihan.noteapp.retrofit.NetworkInterceptor
import com.jihan.noteapp.retrofit.NoteApi
import com.jihan.noteapp.retrofit.UserApi
import com.jihan.noteapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class UserModule {

    @Provides
    @Singleton
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
    }

    @Provides
    @Singleton
    fun providesUserApi(retrofitBuilder: Retrofit.Builder): UserApi {
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(networkInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideNoteApi (retrofitBuilder: Retrofit.Builder ,okHttpClient: OkHttpClient): NoteApi {
        return retrofitBuilder.client(okHttpClient).build().create(NoteApi::class.java)
    }


}