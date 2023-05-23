package nl.michiel.feature.repositories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import nl.michiel.feature.repositories.domain.RepoRepository
import nl.michiel.feature.repositories.domain.entities.Event

class RepoDetailViewModel(
    private val repository: RepoRepository
): ViewModel() {
    fun getRepo(id: Long) = repository.getRepo(id)

    //TODO it would be better to support paging for events, but the Paging library for compose makes preview and unit
    // test code less elegant. It can be done, but this is not a production app anyway
    fun getEvents(id: Long) =
        repository.getEvents(id)
            .map { EventsState.Success(it) }
            .catch { EventsState.Error(it.message ?: "Unknown error") }

}

sealed class EventsState {
    object Loading: EventsState()
    data class Success(val events: List<Event>): EventsState()
    data class Error(val message: String): EventsState()
}
