package nl.michiel.feature.repositories.presentation.di

import nl.michiel.feature.repositories.presentation.viewmodel.RepoDetailViewModel
import nl.michiel.feature.repositories.presentation.viewmodel.RepoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoriesPresentationModule = module {
    viewModel { RepoListViewModel(get()) }
    viewModel { RepoDetailViewModel(get()) }
}
