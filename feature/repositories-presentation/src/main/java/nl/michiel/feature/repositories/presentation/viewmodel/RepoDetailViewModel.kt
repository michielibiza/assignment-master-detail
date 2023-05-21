package nl.michiel.feature.repositories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import nl.michiel.feature.repositories.domain.RepoRepository

class RepoDetailViewModel(
    private val repository: RepoRepository
): ViewModel() {
    fun getRepo(id: Long) = repository.getRepo(id)

    fun getEvents(id: Long) = repository.getEvents(id)
}
