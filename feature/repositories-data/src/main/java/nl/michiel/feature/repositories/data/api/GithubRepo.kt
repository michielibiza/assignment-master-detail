package nl.michiel.feature.repositories.data.api

import nl.michiel.feature.repositories.domain.entities.Repo

data class GithubRepo(
    val id: Long,
    val name: String,
    val description: String?,
    val owner: GithubPerson,
    val stargazers_count: Int,
    val forks_count: Int,
    val open_issues_count: Int,
    val watchers_count: Int,
    val language: String?,
    val created_at: String,
    val updated_at: String,
    val pushed_at: String,
    val homepage: String?,
    val size: Int,
    val default_branch: String,
    val score: Double,
    val topics: List<String>,
    val html_url: String,
) {
    fun toRepo() = Repo(
        id,
        name,
        description,
        stargazers_count,
        forks_count,
        topics,
        owner.toPerson(),
        html_url,
    )
}
