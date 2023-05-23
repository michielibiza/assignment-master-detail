package nl.michiel.feature.repositories.data.api

import nl.michiel.feature.repositories.domain.entities.Person

data class GithubPerson(
    val id: Long,
    val login: String,
    val display_login: String,
    val html_url: String?,
    val avatar_url: String,
) {
    fun toPerson() = Person(
        id,
        login,
        avatar_url,
        html_url ?: "",
    )
}
