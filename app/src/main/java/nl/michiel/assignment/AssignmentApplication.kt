package nl.michiel.assignment

import android.app.Application
import nl.michiel.feature.repositories.data.di.repositoriesDataModule
import nl.michiel.feature.repositories.presentation.di.repositoriesPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
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
                repositoriesDataModule,
            )
        }
    }
}
