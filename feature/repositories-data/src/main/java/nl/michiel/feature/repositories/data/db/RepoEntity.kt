package nl.michiel.feature.repositories.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.michiel.feature.repositories.data.api.GithubRepo
import nl.michiel.feature.repositories.domain.entities.Person
import nl.michiel.feature.repositories.domain.entities.Repo

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var stargazersCount: Int = 0,
    var forksCount: Int = 0,
    var topics: String,
    var url: String,
    // TODO we can make a full relational DB, but for now we just keep it simple
    var ownerId: Long,
    var ownerName: String,
    var ownerAvatarUrl: String,
    var ownerUrl: String,
) {
    fun toRepo() = Repo(
        id = id,
        name = name,
        description = description,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        topics = topics.split(","),
        owner = Person(
            id = ownerId,
            name = ownerName,
            avatarUrl = ownerAvatarUrl,
            url = ownerUrl,
        ),
        url = url,
    )

    companion object {
        fun fromRepo(repo: GithubRepo) =
            RepoEntity(
                id = repo.id,
                name = repo.name,
                description = repo.description ?: "",
                stargazersCount = repo.stargazers_count,
                forksCount = repo.forks_count,
                topics = repo.topics.joinToString(","),
                url = repo.html_url,
                ownerId = repo.owner.id,
                ownerName = repo.owner.login,
                ownerAvatarUrl = repo.owner.avatar_url,
                ownerUrl = repo.owner.html_url ?: "",
            )
    }
}
