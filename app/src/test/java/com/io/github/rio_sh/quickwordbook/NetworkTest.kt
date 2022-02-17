package com.io.github.rio_sh.quickwordbook

import com.io.github.rio_sh.quickwordbook.network.GasService
import com.io.github.rio_sh.quickwordbook.network.Languages
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkTest {
    lateinit var retrofit: Retrofit
    lateinit var service: GasService

    @Before
    fun setUp() {
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(GasService::class.java)
    }

    @Test
    fun getResponseHello() {
        val response = runBlocking {
            val job = async { service.getTranslateResponse("hello", Languages.ENGLISH.languageCode, Languages.JAPANESE.languageCode) }
            job.await()
        }

        assertThat(response.body()?.code).isEqualTo(200)
        assertThat(response.body()?.text).isEqualTo("こんにちは")
    }

    @Test
    fun getResponseEmpty() {
        val response = runBlocking {
            val job = async { service.getTranslateResponse("", Languages.ENGLISH.languageCode, Languages.JAPANESE.languageCode) }
            job.await()
        }

        assertThat(response.body()?.code).isEqualTo(400)
        assertThat(response.body()?.text).isEqualTo("Bad Request")
    }
}