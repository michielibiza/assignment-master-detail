package nl.michiel.domain.github.entities

data class Event(
    val id: Int,
    val type: String,
    val actor: Person,
    val repo: Repo,
    val createdAt: String,
)
