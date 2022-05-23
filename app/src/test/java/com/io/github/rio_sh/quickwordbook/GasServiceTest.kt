/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook

import com.io.github.rio_sh.quickwordbook.network.GasService
import com.io.github.rio_sh.quickwordbook.network.Languages
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GasServiceTest {
    private val mockWebServer = MockWebServer()

    lateinit var retrofit: Retrofit
    lateinit var service: GasService

    @Before
    fun setUp() {
        mockWebServer.start()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(GasService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getResponseHello() {
        val response = runBlocking {
            mockWebServer.enqueue(MockResponse().setBody(dummy200()))

            val job = async {
                service.getTranslateResponse(
                    "hello",
                    Languages.ENGLISH.languageCode,
                    Languages.JAPANESE.languageCode
                )
            }
            job.await()
        }

        assertThat(response.body()?.code).isEqualTo(200)
        assertThat(response.body()?.text).isEqualTo("こんにちは")
    }

    @Test
    fun getResponseEmpty() {
        val response = runBlocking {
            mockWebServer.enqueue(MockResponse().setBody(dummy400()))

            val job = async {
                service.getTranslateResponse(
                    "",
                    Languages.ENGLISH.languageCode,
                    Languages.JAPANESE.languageCode
                )
            }
            job.await()
        }

        assertThat(response.body()?.code).isEqualTo(400)
        assertThat(response.body()?.text).isEqualTo("Bad Request")
    }
}

private fun dummy200(): String =
    """
        {
        "code": 200,
        "text": "こんにちは"
        }
    """.trimIndent()

private fun dummy400(): String =
    """
        {
        "code": 400,
        "text": "Bad Request"
        }
    """.trimIndent()
