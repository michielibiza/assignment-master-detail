package nl.michiel.feature.repositories.presentation.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import nl.michiel.feature.repositories.domain.RepoRepository
import nl.michiel.feature.repositories.domain.entities.Event

class RepoDetailViewModel(
    private val repository: RepoRepository
): ViewModel() {
    fun getRepo(id: Long) = repository.getRepo(id)

    fun getEvents(id: Long) =
        repository.getEvents(id)
            .map { EventsState.Success(it) as EventsState }
            .catch { EventsState.Error(it.message ?: "Unknown error") }

}

sealed class EventsState {
    object Loading: EventsState()
    data class Success(val events: List<Event>): EventsState()
    data class Error(val message: String): EventsState()
}
