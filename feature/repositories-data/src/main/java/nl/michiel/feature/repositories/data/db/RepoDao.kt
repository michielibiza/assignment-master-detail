package nl.michiel.feature.repositories.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {
    @Query("SELECT * FROM repos WHERE id = :id")
    fun getRepo(id: Long): Flow<RepoEntity>

    @Query("SELECT * FROM repos")
    fun getRepos(): Flow<List<RepoEntity>>

    @Upsert
    suspend fun upsertRepos(repos: List<RepoEntity>)
}
