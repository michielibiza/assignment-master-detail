package nl.michiel.feature.repositories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import nl.michiel.domain.github.RepoRepository

class RepoListViewModel(
    private val repository: RepoRepository
): ViewModel() {
    val state: StateFlow<RepoListState> =
        repository.getRepos()
            .map { RepoListState.Success(it) as RepoListState }
            .onStart { emit(RepoListState.Loading) }
            .catch { emit(RepoListState.Error(it.message ?: "Unknown error", ::sync)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RepoListState.Loading
            )

    fun sync() {
        repository.sync()
    }
}
