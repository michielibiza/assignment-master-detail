package nl.michiel.feature.repositories.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepoEntity::class], version = 1)
abstract class RepoDatabase: RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
