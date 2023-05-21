package nl.michiel.feature.repositories.domain.entities

data class Event(
    val id: Long,
    val type: String,
    val actor: Person,
    val createdAt: String,
)
