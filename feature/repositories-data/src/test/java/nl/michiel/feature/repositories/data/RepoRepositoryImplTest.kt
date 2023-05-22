package nl.michiel.feature.repositories.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import nl.michiel.feature.repositories.data.api.GithubService
import nl.michiel.feature.repositories.data.db.RepoDao
import nl.michiel.feature.repositories.data.db.RepoEntity
import nl.michiel.feature.repositories.data.fixtures.events
import nl.michiel.feature.repositories.data.fixtures.reposInfinum
import nl.michiel.feature.repositories.data.fixtures.reposJakeWharton
import org.junit.Before
import org.junit.Test


class RepoRepositoryImplTest {

    private val service = mockk<GithubService>()
    private val repoDao = mockk<RepoDao>()

    @Before
    fun setUp() {
        coEvery { service.getRepos("JakeWharton") } returns reposJakeWharton
        coEvery { service.getRepos("infinum") } returns reposInfinum
        coEvery { service.getEvents(any(), any()) } returns events

        coEvery { repoDao.upsertRepos(any()) } returns Unit
        every { repoDao.getRepo(any()) } returns flowOf(RepoEntity.fromRepo(reposJakeWharton.first()))
    }

    @Test
    fun `RepoRepositoryImpl getEvents() passes the name and author of the repo to GithubService`() = runTest {
        val repoRepository = RepoRepositoryImpl(repoDao, service)

        repoRepository.getEvents(1L).collect()

        coVerify { service.getEvents("abs.io", "JakeWharton") }
    }

    @Test
    fun `RepoRepositoryImpl sync() calls getRepo() for all users`() = runTest {
        val repoRepository = RepoRepositoryImpl(repoDao, service)

        repoRepository.sync()

        coVerify(exactly = 1) { service.getRepos("JakeWharton") }
        coVerify(exactly = 1) { service.getRepos("infinum") }
    }

    @Test
    fun `RepoRepositoryImpl sync() calls upsertRepos() for all users`() = runTest {
        val repoRepository = RepoRepositoryImpl(repoDao, service)

        repoRepository.sync()

        coVerify(exactly = 2) { repoDao.upsertRepos(any()) }
    }

}
