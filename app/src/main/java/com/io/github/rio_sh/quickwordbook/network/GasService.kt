package com.io.github.rio_sh.quickwordbook.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GasService {
    // Refactor to using Json object
    /**
     * @param text text to translate
     * @param source Language of source text. Specify from value of [Languages]
     * @param target Language of text will be translate. Specify from value of [Languages]
     */
    @GET("exec")
    suspend fun getTranslateResponse(
        @Query("text") text: String,
        @Query("source") source: String,
        @Query("target") target: String
    ): Response<ResponseText>
}

sealed class Languages(val languageCode: String) {
    object ENGLISH : Languages("en")
    object JAPANESE : Languages("ja")
}