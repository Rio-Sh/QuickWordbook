/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.github.rio_sh.quickwordbook.data.DefaultRepository
import com.io.github.rio_sh.quickwordbook.data.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    val isLoading: Boolean

    data class NoWords(
        override val isLoading: Boolean
    ) : HomeUiState

    data class HasWords(
        override val isLoading: Boolean,
        val words: List<Word>,
        val isWordsLoadingFailed: Boolean
    ) : HomeUiState
}

private data class HomeViewModelState(
    val isLoading: Boolean = false,
    val words: List<Word> = emptyList(),
    val isWordsLoadingFailed: Boolean = false
) {
    fun toUiState(): HomeUiState =
        if (words.isEmpty()) {
            HomeUiState.NoWords(
                isLoading = isLoading
            )
        } else {
            HomeUiState.HasWords(
                isLoading = isLoading,
                words = words,
                isWordsLoadingFailed = isWordsLoadingFailed
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
            defaultRepository.observeLastEditFive()
                .catch {
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            isWordsLoadingFailed = true
                        )
                    }
                }
                .collect { words ->
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
