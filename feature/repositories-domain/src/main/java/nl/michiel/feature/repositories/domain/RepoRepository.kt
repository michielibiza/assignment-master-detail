package nl.michiel.feature.repositories.domain

import kotlinx.coroutines.flow.Flow
import nl.michiel.feature.repositories.domain.entities.Event
import nl.michiel.feature.repositories.domain.entities.Repo

interface RepoRepository {
    fun getRepos(): Flow<List<Repo>>

    fun getRepo(id: Long): Flow<Repo>

    fun getEvents(id: Long): Flow<List<Event>>

    suspend fun sync()
}
