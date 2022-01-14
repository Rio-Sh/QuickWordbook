package com.io.github.rio_sh.quickwordbook.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GasService {
    // Refactor to using Json object
    /**
     * @param text text to translate
     * @param source Language of source text. Specify from value of [Language]
     * @param target Language of text will be translate. Specify from value of [Language]
     */
    @GET("exec")
    fun getTranslateResponse(
        @Query("text") text: String,
        @Query("source") source: String,
        @Query("target") target: String
    ): Call<ResponseText>
}

object Language {
    const val ENGLISH = "en"
    const val JAPANESE = "ja"
}