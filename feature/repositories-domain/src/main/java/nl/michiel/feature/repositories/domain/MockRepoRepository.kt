package nl.michiel.feature.repositories.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import nl.michiel.feature.repositories.domain.entities.Event
import nl.michiel.feature.repositories.domain.entities.Person
import nl.michiel.feature.repositories.domain.entities.Repo

class MockRepoRepository: RepoRepository {
    private fun person(id: Int) = Person(id, "author $id", "https://randomuser.me/api/portraits/thumb/men/$id.jpg")

    val repoData = List(12) { i ->
        Repo(i, "repo $i", "description $i", i+3, i, emptyList(), person(i % 3))
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
        Event(i, type, person(i % 3), repoData[i % repoData.size], "2020-01-01")
    }

    override fun getRepos() = flowOf(repoData).onStart { delay(500) }

    override fun getRepo(id: Int) = flowOf(repoData[id % repoData.size])

    override fun getEvents(id: Int) = flowOf(eventData)

    override fun sync() {}
}
