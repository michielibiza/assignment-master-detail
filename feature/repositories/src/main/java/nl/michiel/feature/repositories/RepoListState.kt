package nl.michiel.feature.repositories

import nl.michiel.domain.github.entities.Repo

sealed class RepoListState {
    object Loading : RepoListState()
    data class Error(val message: String) : RepoListState()
    data class Success(val repos: List<Repo>) : RepoListState()
}
