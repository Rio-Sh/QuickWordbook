package com.io.github.rio_sh.quickwordbook.ui.common

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.io.github.rio_sh.quickwordbook.network.Languages

private const val TAG = "TranslateCardUiState"

// sample code is here https://developers.google.com/ml-kit/language/translation/android
fun identifyJapaneseOrNot(sourceText: String,
                          changeLangSettingsWhenIdentify: (String) -> Unit
) {
    val languageIdentifier = LanguageIdentification.getClient()
    languageIdentifier.identifyLanguage(sourceText)
        .addOnFailureListener { exception ->
            Log.e(TAG, "Failed to identify language $exception")
        }
        //Change uiState after the identify succeed
        .addOnSuccessListener { languageCode ->
            when (languageCode) {
                "und" -> {
                    Log.i(TAG,"Can't identify language.")
                    changeLangSettingsWhenIdentify(languageCode)
                }
                "ja" -> {
                    Log.i(TAG, "Language: $languageCode")
                    changeLangSettingsWhenIdentify(languageCode)
                }
                else -> {
                    Log.i(TAG, "Not Japanese language")
                }
            }
        }
}

fun translateByMlkit(
    sourceLang: Languages,
    sourceText: String,
    changeTargetText: (String) -> Unit
) {
    var sourceLanguage = TranslateLanguage.JAPANESE
    var targetLanguage = TranslateLanguage.ENGLISH
    if (sourceLang == Languages.ENGLISH){
        sourceLanguage = TranslateLanguage.ENGLISH
        targetLanguage = TranslateLanguage.JAPANESE
    }
    val options = TranslatorOptions.Builder()
        .setSourceLanguage(sourceLanguage)
        .setTargetLanguage(targetLanguage)
        .build()
    val languageTranslator = Translation.getClient(options)
    var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()
    languageTranslator.downloadModelIfNeeded(conditions)
        .addOnSuccessListener {
            // Model downloaded successfully. Okay to start translating.
            // (Set a flag, unhide the translation UI, etc.)
            Log.i(TAG, "Succeed to loading translation model")
            languageTranslator.translate(sourceText)
                .addOnSuccessListener { translatedText ->
                    // Translation successful.
                    changeTargetText(translatedText)
                }
                .addOnFailureListener { exception ->
                    // Error.
                    Log.e(TAG, "Failed to translation : $exception")
                }
        }
        .addOnFailureListener { exception ->
            // Model couldn’t be downloaded or other internal error.
            Log.e(TAG, "Failed to download Language Translator model : $exception")
        }
}