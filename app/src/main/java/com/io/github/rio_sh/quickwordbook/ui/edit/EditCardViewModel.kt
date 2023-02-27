/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.ui.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.github.rio_sh.quickwordbook.R
import com.io.github.rio_sh.quickwordbook.data.DefaultRepository
import com.io.github.rio_sh.quickwordbook.data.Word
import com.io.github.rio_sh.quickwordbook.network.Languages
import com.io.github.rio_sh.quickwordbook.ui.common.ErrorMessage
import com.io.github.rio_sh.quickwordbook.ui.common.identifyJapaneseOrNot
import com.io.github.rio_sh.quickwordbook.ui.common.translateByMlkit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @param word
 * @param sourceText
 * @param targetText
 * @param isTargetTextLoading
 * @param sourceLanguage
 * @param targetLanguage
 * @param isSwitchChecked
 */
data class EditCardUiState(
    val word: Word = Word(-1, "", "", -1L),
    val sourceText: String = "",
    val targetText: String = "",
    val isTargetTextLoading: Boolean = false,
    val sourceLanguage: Languages = Languages.ENGLISH,
    val targetLanguage: Languages = Languages.JAPANESE,
    val isSwitchChecked: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val currentMessage: ErrorMessage = ErrorMessage(-1, R.string.empty_string)
)

private const val TAG = "EditCardViewModel"

@HiltViewModel
class EditCardViewModel @Inject constructor(
    private val defaultRepository: DefaultRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditCardUiState())
    val uiState: StateFlow<EditCardUiState> = _uiState.asStateFlow()

    init {
        // collect error message and display on the screen
        viewModelScope.launch {
            _uiState.collect {
                if (it.errorMessages.isNotEmpty()) {
                    val message = _uiState.value.errorMessages[0]
                    setErrorMessage(message)
                    delay(2000)
                    errorMessageShown(message.id)
                }
            }
        }
    }

    fun upDateWord() {
        viewModelScope.launch {
            defaultRepository.updateWord(
                Word(
                    wordId = _uiState.value.word.wordId,
                    textSource = _uiState.value.sourceText,
                    textTarget = _uiState.value.targetText
                )
            )
        }
    }

    fun setWord(wordId: Int) {
        viewModelScope.launch {
            val word = defaultRepository.getWordById(wordId)
            _uiState.update {
                it.copy(
                    word = word,
                    sourceText = word.textSource,
                    targetText = word.textTarget
                )
            }
        }
    }

    fun changeSourceText(newText: String) {
        _uiState.update { it.copy(sourceText = newText) }
    }

    fun changeTargetText(newText: String) {
        _uiState.update { it.copy(targetText = newText) }
    }

    fun toggleSwitch(changedValue: Boolean) {
        _uiState.update { it.copy(isSwitchChecked = changedValue) }
    }

    fun changeTargetLanguage() {
        when (_uiState.value.targetLanguage) {
            Languages.ENGLISH -> _uiState.update {
                it.copy(
                    sourceLanguage = Languages.ENGLISH,
                    targetLanguage = Languages.JAPANESE
                )
            }
            Languages.JAPANESE -> _uiState.update {
                it.copy(
                    sourceLanguage = Languages.JAPANESE,
                    targetLanguage = Languages.ENGLISH
                )
            }
        }
    }

    private fun changeLangSettingsWhenIdentify(identifiedLanguageCode: String) {
        if (!_uiState.value.isSwitchChecked
            && identifiedLanguageCode == "ja") {
            changeTargetLanguage()
            toggleSwitch(!_uiState.value.isSwitchChecked)
            Log.d(TAG, "uiState language toggled to on")
        } else if (_uiState.value.isSwitchChecked
            && identifiedLanguageCode == "und") {
            changeTargetLanguage()
            toggleSwitch(!_uiState.value.isSwitchChecked)
            Log.d(TAG, "uiState language toggled to off")
        }
    }


    private fun setErrorMessage(message: ErrorMessage) {
        _uiState.update { it.copy(currentMessage = message) }
    }

    /**
     * Remove message from ui state.
     */
    private fun errorMessageShown(id: Long) {
        _uiState.update { currentState ->
            val errorMessages = currentState.errorMessages.filterNot { it.id == id }
            currentState.copy(
                errorMessages = errorMessages,
                currentMessage = ErrorMessage(-1, R.string.empty_string)
            )
        }
    }
    fun identifyText() {
        identifyJapaneseOrNot(
            sourceText = _uiState.value.sourceText,
            changeLangSettingsWhenIdentify = { changeLangSettingsWhenIdentify(it) }
        )
    }

    /**
     * identify language and translate it by Mlkit
     */
    fun translateText() {
            translateByMlkit(
                sourceLang = _uiState.value.sourceLanguage,
                sourceText = _uiState.value.sourceText,
                changeTargetText = { changeTargetText(it) }
            )
    }
}
