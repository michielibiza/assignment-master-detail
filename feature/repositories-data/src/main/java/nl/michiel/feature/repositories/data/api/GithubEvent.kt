package nl.michiel.feature.repositories.data.api

import nl.michiel.feature.repositories.domain.entities.Event

data class GithubEvent(
    val id: Long,
    val type: String,
    val actor: GithubPerson,
    val payload: Payload,
    val public: Boolean,
    val created_at: String,
) {
    fun toEvent() = Event(
        id,
        type,
        actor.toPerson(),
        created_at,
    )
}

data class Payload(
    val action: String,
    val issue: Issue?,
    val comment: Comment?,
    val pull_request: PullRequest?,
    val forkee: Forkee?,
)

data class Issue(
    val id: Long,
    val number: Int,
    val title: String,
    val user: GithubPerson,
    val assignee: GithubPerson?,
    val assignees: List<GithubPerson>,
    val comments: Int,
    val body: String,
)

data class Comment(
    val id: Long,
    val user: GithubPerson,
    val body: String,
)

data class PullRequest(
    val id: Long,
    val number: Int,
    val title: String,
    val user: GithubPerson,
    val assignee: GithubPerson?,
    val assignees: List<GithubPerson>,
    val comments: Int,
    val body: String,
)

data class Forkee(
    val id: Long,
    val name: String,
    val full_name: String,
    val owner: GithubPerson,
)
