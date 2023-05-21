package nl.michiel.feature.repositories.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}/repos")
    suspend fun getRepos(
        @Path("user") user: String,
    ): List<GithubRepo>

    @GET("repos/{repo}/{user}/events")
    suspend fun getEvents(
        @Path("user") userName: String,
        @Path("repo") repoName: String,
    ): List<GithubEvent>

}
