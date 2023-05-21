package nl.michiel.feature.repositories.viewmodel

import nl.michiel.domain.github.RepoRepository

class RepoDetailViewModel(
    private val repository: RepoRepository
) {
    fun getRepo(id: Int) = repository.getRepo(id)

    fun getEvents(id: Int) = repository.getEvents(id)
}
