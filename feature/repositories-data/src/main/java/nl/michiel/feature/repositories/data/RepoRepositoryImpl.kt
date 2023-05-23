package nl.michiel.feature.repositories.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import nl.michiel.feature.repositories.data.api.GithubService
import nl.michiel.feature.repositories.data.db.RepoDao
import nl.michiel.feature.repositories.data.db.RepoEntity
import nl.michiel.feature.repositories.domain.RepoRepository
import nl.michiel.feature.repositories.domain.entities.Event

class RepoRepositoryImpl(
    private val repoDao: RepoDao,
    private val githubService: GithubService,
): RepoRepository {

        override fun getRepos() =
            repoDao.getRepos()
                .map { repos ->
                    repos.map { it.toRepo() }
                }

        override fun getRepo(id: Long) =
            repoDao.getRepo(id)
                .map { it.toRepo() }

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun getEvents(id: Long): Flow<List<Event>> =
            getRepo(id).mapLatest { repo ->
                githubService.getEvents(repo.name, repo.owner.name)
                    .map { it.toEvent() }
            }

        override suspend fun sync() {
            // TODO we should store the last synced time and only update if some amount of time has passed
            FOLLOWED_USERS.forEach { user ->
                var page = 1
                var hasMore = true
                while (hasMore) {
                    githubService.getRepos(user, page)
                        .also { hasMore = it.isNotEmpty() }
                        .map { RepoEntity.fromRepo(it) }
                        .let { repos ->
                            if (repos.isNotEmpty()) {
                                repoDao.upsertRepos(repos)
                            }
                        }
                    page++
                }
            }
        }

    companion object {
        // TODO this data should come from a storage module that is edited by a different feature
        private val FOLLOWED_USERS = listOf("JakeWharton", "infinum")
    }
}
