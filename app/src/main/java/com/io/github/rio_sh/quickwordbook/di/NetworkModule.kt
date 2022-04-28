/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.di

import com.io.github.rio_sh.quickwordbook.BuildConfig
import com.io.github.rio_sh.quickwordbook.network.GasService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideGasService(retrofit: Retrofit): GasService = retrofit.create(GasService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
