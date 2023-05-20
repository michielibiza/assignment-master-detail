package nl.michiel.domain.github

import kotlinx.coroutines.flow.Flow
import nl.michiel.domain.github.entities.Repo

interface RepoRepository {
    fun getRepos(): Flow<List<Repo>>
}
