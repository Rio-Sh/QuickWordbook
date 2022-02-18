package com.io.github.rio_sh.quickwordbook.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.github.rio_sh.quickwordbook.data.DefaultRepository
import com.io.github.rio_sh.quickwordbook.data.Word
import com.io.github.rio_sh.quickwordbook.network.Languages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @param sourceText
 * @param targetText
 * @param isTargetTextLoading
 * @param sourceLanguages
 * @param targetLanguage
 * @param isSwitchChecked
 */
data class AddCardUiState(
    val sourceText: String = "",
    val targetText: String = "",
    val isTargetTextLoading: Boolean = false,
    val sourceLanguages: Languages = Languages.ENGLISH,
    val targetLanguage: Languages = Languages.JAPANESE,
    val isSwitchChecked: Boolean = false
)

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val defaultRepository: DefaultRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddCardUiState())
    val uiState: StateFlow<AddCardUiState> = _uiState.asStateFlow()

    fun addWord() {
        viewModelScope.launch {
            defaultRepository.insertWord(
                Word(
                    textSource = _uiState.value.sourceText,
                    textTarget = _uiState.value.targetText
                )
            )
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
        when(_uiState.value.targetLanguage) {
            Languages.ENGLISH -> _uiState.update { it.copy(
                sourceLanguages = Languages.ENGLISH,
                targetLanguage = Languages.JAPANESE
            ) }
            Languages.JAPANESE -> _uiState.update { it.copy(
                sourceLanguages = Languages.JAPANESE,
                targetLanguage = Languages.ENGLISH
            ) }
        }
    }

    /**
     * Call GasApi using current source text state
     */
    fun translateText() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isTargetTextLoading = true) }
                val response = defaultRepository.translateText(
                    sourceText = _uiState.value.sourceText,
                    sourceLanguage = _uiState.value.sourceLanguages,
                    targetLanguages = _uiState.value.targetLanguage
                )
                val targetText = response.body()?.text ?: ""
                _uiState.update { it.copy(
                    targetText = targetText,
                    isTargetTextLoading = false
                ) }
            } catch(e: Exception) {
                // TODO Error handling
                println(e)
            }
        }
    }
}
