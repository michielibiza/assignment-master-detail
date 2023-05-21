package nl.michiel.feature.repositories.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import nl.michiel.feature.repositories.domain.entities.Event
import nl.michiel.feature.repositories.domain.entities.Person
import nl.michiel.feature.repositories.domain.entities.Repo

class MockRepoRepository: RepoRepository {
    private fun person(id: Long) = Person(id, "author $id", "https://randomuser.me/api/portraits/thumb/men/$id.jpg")

    val repoData = List(12) { i ->
        Repo(i.toLong(), "repo $i", "description $i", i+3, i, emptyList(), person(i % 3L))
    }

    val eventData = List(15) { i ->
        val type = when(i) {
            in 0..4 -> "PushEvent"
            in 4..6 -> "WatchEvent"
            in 6..8 -> "IssueCommentEvent"
            in 8..10 -> "IssuesEvent"
            in 10..13 -> "ForkEvent"
            else -> "CreateEvent"
        }
        Event(i.toLong(), type, person(i % 3L), "2020-01-01")
    }

    override fun getRepos() = flowOf(repoData).onStart { delay(500) }

    override fun getRepo(id: Long) = flowOf(repoData[id.toInt() % repoData.size])

    override fun getEvents(id: Long) = flowOf(eventData)

    override suspend fun sync() {}
}
