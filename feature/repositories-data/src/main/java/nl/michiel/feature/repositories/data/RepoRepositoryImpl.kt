package nl.michiel.feature.repositories.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import nl.michiel.feature.repositories.data.api.GithubService
import nl.michiel.feature.repositories.data.db.RepoDao
import nl.michiel.feature.repositories.domain.RepoRepository
import nl.michiel.feature.repositories.domain.entities.Event

class RepoRepositoryImpl(
    private val repoDao: RepoDao,
    private val githubService: GithubService,
): RepoRepository {

        override fun getRepos() = repoDao.getRepos()

        override fun getRepo(id: Long) = repoDao.getRepo(id)

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun getEvents(id: Long): Flow<List<Event>> =
            getRepo(id).mapLatest { repo ->
                githubService.getEvents(repo.name, repo.owner.name)
                    .map { it.toEvent() }
            }

        override suspend fun sync() {
            val jakesRepos = githubService.getRepos("jakeWharton")
            repoDao.insertRepos(jakesRepos)
            val infinumRepos = githubService.getRepos("infinum")
            repoDao.insertRepos(infinumRepos)
        }
}
