/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.network

/**
 * Using for Gson converter, Convert Json object response from Gas API.
 */
data class ResponseText(
    val code: Int,
    val text: String
)
