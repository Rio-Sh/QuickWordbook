package com.io.github.rio_sh.quickwordbook.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.github.rio_sh.quickwordbook.data.DefaultWordsRepository
import com.io.github.rio_sh.quickwordbook.data.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CardsUiState(
    val isLoading: Boolean = false,
    val isCardsOpen: Boolean = false,
    val words: List<Word> = emptyList()
)

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val defaultWordsRepository: DefaultWordsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CardsUiState(isLoading = false))
    val uiState: StateFlow<CardsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            defaultWordsRepository.observeAllWords().collect { words ->
                _uiState.update { it.copy(words = words, isLoading = false) }
            }
        }
    }

    fun expandOrCloseCards() {
        _uiState.update { it.copy(isCardsOpen = true) }
    }

    fun deleteWord(wordId: Int) {
        viewModelScope.launch {
            defaultWordsRepository.deleteWordById(wordId)
        }
    }

    fun deleteAllWords() {
        viewModelScope.launch {
            defaultWordsRepository.deleteAllWord()
        }
    }
}