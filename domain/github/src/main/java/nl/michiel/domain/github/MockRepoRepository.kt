package nl.michiel.domain.github

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import nl.michiel.domain.github.entities.Owner
import nl.michiel.domain.github.entities.Repo

class MockRepoRepository: RepoRepository {
    private val data = List(12) { i ->
        val o = i % 3
        Repo(i, "repo $i", "description $i", i+3, i, emptyList(), Owner(o, "author $o", "https://randomuser.me/api/portraits/thumb/men/$o.jpg"))
    }

    override fun getRepos() = flowOf(data).onStart { delay(500) }

    override fun getRepo(id: Int) = flowOf(data[id % data.size])

    override fun sync() {}
}
