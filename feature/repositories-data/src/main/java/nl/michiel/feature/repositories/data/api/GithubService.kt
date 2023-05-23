package nl.michiel.feature.repositories.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users/{user}/repos")
    suspend fun getRepos(
        @Path("user") user: String,
        @Query("page") page: Int = 1,
    ): List<GithubRepo>

    @GET("repos/{repo}/{user}/events")
    suspend fun getEvents(
        @Path("user") userName: String,
        @Path("repo") repoName: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int = 10,
    ): List<GithubEvent>

}
