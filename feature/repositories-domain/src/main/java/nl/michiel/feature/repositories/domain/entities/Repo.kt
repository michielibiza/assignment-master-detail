package nl.michiel.feature.repositories.domain.entities

data class Repo(
    val id: Int,
    val name: String,
    val description: String?,
    val stargazersCount: Int,
    val forksCount: Int,
    val topics: List<String>,
    val owner: Person,
)
