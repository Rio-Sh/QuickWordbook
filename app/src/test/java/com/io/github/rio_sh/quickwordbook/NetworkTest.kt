package com.io.github.rio_sh.quickwordbook

import com.io.github.rio_sh.quickwordbook.network.GasService
import com.io.github.rio_sh.quickwordbook.network.Language
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
        val get = service.getTranslateResponse("hello", Language.ENGLISH, Language.JAPANESE)
        val response = runBlocking {
            get.execute()
        }

        assertThat(response.body()?.code).isEqualTo(200)
        assertThat(response.body()?.text).isEqualTo("こんにちは")
    }

    @Test
    fun getResponseEmpty() {
        val get = service.getTranslateResponse("", Language.ENGLISH, Language.JAPANESE)
        val response = runBlocking {
            get.execute()
        }

        assertThat(response.body()?.code).isEqualTo(400)
        assertThat(response.body()?.text).isEqualTo("Bad Request")
    }
}