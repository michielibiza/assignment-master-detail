package nl.michiel.feature.repositories.data.di

import androidx.room.Room
import nl.michiel.feature.repositories.data.RepoRepositoryImpl
import nl.michiel.feature.repositories.data.api.GithubService
import nl.michiel.feature.repositories.data.db.RepoDatabase
import nl.michiel.feature.repositories.domain.RepoRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val repositoriesDataModule = module {

    single<RepoRepository> { RepoRepositoryImpl(get(), get()) }

    // Room DB
    single<RepoDatabase> {
        Room.databaseBuilder(
            get(),
            RepoDatabase::class.java,
            "repos-database",
        ).build()
    }

    // Room DAO
    single { get<RepoDatabase>().repoDao() }

    // Retrofit service
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(GithubService::class.java)
    }
}
