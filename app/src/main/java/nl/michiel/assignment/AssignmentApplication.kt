package nl.michiel.assignment

import android.app.Application
import nl.michiel.feature.repositories.domain.MockRepoRepository
import nl.michiel.feature.repositories.domain.RepoRepository
import nl.michiel.feature.repositories.presentation.di.repositoriesPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class AssignmentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        setupDI()
    }

    private fun setupDI() {
        startKoin {
            androidContext(this@AssignmentApplication)
            androidLogger()
            modules(
                repositoriesPresentationModule,
                mockModule,
            )
        }
    }

    private val mockModule = module {
        single<RepoRepository> { MockRepoRepository() }
    }
}
