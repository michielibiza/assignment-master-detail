package nl.michiel.feature.repositories.presentation.viewmodel

import nl.michiel.feature.repositories.domain.entities.Repo

sealed class RepoListState {
    object Loading : RepoListState()
    data class Error(val message: String, val onRetry: () -> Unit) : RepoListState()
    data class Success(val repos: List<Repo>) : RepoListState()
}
