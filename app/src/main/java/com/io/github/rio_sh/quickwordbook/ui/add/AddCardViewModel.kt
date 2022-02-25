package com.io.github.rio_sh.quickwordbook.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.github.rio_sh.quickwordbook.R
import com.io.github.rio_sh.quickwordbook.data.DefaultRepository
import com.io.github.rio_sh.quickwordbook.data.Word
import com.io.github.rio_sh.quickwordbook.network.Languages
import com.io.github.rio_sh.quickwordbook.ui.common.ErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
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
    val isSwitchChecked: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val currentMessage: ErrorMessage = ErrorMessage(-1, R.string.empty_string)
)

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val defaultRepository: DefaultRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddCardUiState())
    val uiState: StateFlow<AddCardUiState> = _uiState.asStateFlow()

    init {
        // collect error message and display on the screen
        viewModelScope.launch {
            _uiState.collect {
                if(it.errorMessages.isNotEmpty()){
                    val message = _uiState.value.errorMessages[0]
                    setErrorMessage(message)
                    delay(2000)
                    errorMessageShown(message.id)
                }
            }
        }
    }

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
        when (_uiState.value.targetLanguage) {
            Languages.ENGLISH -> _uiState.update {
                it.copy(
                    sourceLanguages = Languages.ENGLISH,
                    targetLanguage = Languages.JAPANESE
                )
            }
            Languages.JAPANESE -> _uiState.update {
                it.copy(
                    sourceLanguages = Languages.JAPANESE,
                    targetLanguage = Languages.ENGLISH
                )
            }
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

    /**
     * Call GasApi using current source text state.
     * If failed, add message to ui state.
     */
    fun translateText() {
        viewModelScope.launch {
            runCatching {
                _uiState.update { it.copy(isTargetTextLoading = true) }
                defaultRepository.translateText(
                    sourceText = _uiState.value.sourceText,
                    sourceLanguage = _uiState.value.sourceLanguages,
                    targetLanguages = _uiState.value.targetLanguage
                )
            }.onSuccess { response ->
                if(response.body()!!.code == 400){
                    val errorMessages = _uiState.value.errorMessages + ErrorMessage(
                        id = UUID.randomUUID().mostSignificantBits,
                        stringId = R.string.bad_request
                    )
                    _uiState.update { it.copy(errorMessages = errorMessages) }
                } else {
                    val targetText = response.body()?.text ?: ""
                    _uiState.update { it.copy( targetText = targetText) }
                }
            }.onFailure {
                val errorMessages = _uiState.value.errorMessages + ErrorMessage(
                    id = UUID.randomUUID().mostSignificantBits,
                    stringId = R.string.sorry_something_wrong_cant_translate
                )
                _uiState.update { it.copy(errorMessages = errorMessages) }
            }.also {
                _uiState.update { it.copy(isTargetTextLoading = false) }
            }
        }
    }
}
