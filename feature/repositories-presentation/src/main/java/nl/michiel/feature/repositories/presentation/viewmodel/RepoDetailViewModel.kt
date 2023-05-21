package nl.michiel.feature.repositories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import nl.michiel.feature.repositories.domain.RepoRepository

class RepoDetailViewModel(
    private val repository: RepoRepository
): ViewModel() {
    fun getRepo(id: Int) = repository.getRepo(id)

    fun getEvents(id: Int) = repository.getEvents(id)
}
