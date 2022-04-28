/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.github.rio_sh.quickwordbook.data.DefaultRepository
import com.io.github.rio_sh.quickwordbook.data.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CardsUiState(
    val isLoading: Boolean = false,
    val isCardsOpen: Boolean = false,
    val words: List<Word> = emptyList(),
    val isWordsLoadingFailed: Boolean = false
)

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val defaultRepository: DefaultRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CardsUiState(isLoading = false))
    val uiState: StateFlow<CardsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            defaultRepository.observeAllWords()
                .catch {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isWordsLoadingFailed = true
                        )
                    }
                }
                .collect { words ->
                    _uiState.update { it.copy(words = words, isLoading = false) }
                }
        }
    }

    fun toggleCardsExpand(value: Boolean) {
        _uiState.update { it.copy(isCardsOpen = value) }
    }

    fun deleteWord(wordId: Int) {
        viewModelScope.launch {
            defaultRepository.deleteWordById(wordId)
        }
    }

    fun deleteAllWords() {
        viewModelScope.launch {
            defaultRepository.deleteAllWord()
        }
    }
}
