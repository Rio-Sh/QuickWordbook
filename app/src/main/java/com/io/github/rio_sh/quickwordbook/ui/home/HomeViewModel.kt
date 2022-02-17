package com.io.github.rio_sh.quickwordbook.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.github.rio_sh.quickwordbook.data.DefaultRepository
import com.io.github.rio_sh.quickwordbook.data.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState{
    val isLoading: Boolean

    data class NoWords(
        override val isLoading: Boolean
    ) : HomeUiState

    data class HasWords(
        override val isLoading: Boolean,
        val words: List<Word>
    ) : HomeUiState
}

private data class HomeViewModelState(
    val isLoading: Boolean = false,
    val words: List<Word> = emptyList()
){
    fun toUiState(): HomeUiState =
        if (words.isEmpty()) {
            HomeUiState.NoWords(
                isLoading = isLoading
            )
        } else {
            HomeUiState.HasWords(
                isLoading = isLoading,
                words = words
            )
        }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val defaultRepository: DefaultRepository
) : ViewModel() {
    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = false))

    // expose to UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            defaultRepository.observeLastEditFive().collect { words ->
                viewModelState.update { it.copy(words = words, isLoading = false) }
            }
        }
    }

    fun deleteWord(wordId: Int) {
        viewModelScope.launch {
            defaultRepository.deleteWordById(wordId)
        }
    }

}