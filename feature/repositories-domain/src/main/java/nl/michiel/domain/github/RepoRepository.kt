package nl.michiel.domain.github

import kotlinx.coroutines.flow.Flow
import nl.michiel.domain.github.entities.Event
import nl.michiel.domain.github.entities.Repo

interface RepoRepository {
    fun getRepos(): Flow<List<Repo>>

    fun getRepo(id: Int): Flow<Repo>

    fun getEvents(id: Int): Flow<List<Event>>

    fun sync()
}
