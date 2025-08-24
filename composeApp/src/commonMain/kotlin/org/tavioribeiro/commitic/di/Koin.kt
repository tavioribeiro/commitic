package org.tavioribeiro.commitic.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.ProjectLocalDataSource
import org.tavioribeiro.commitic.data.repository.ProjectRepositoryImpl
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.usecase.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.usecase.SaveProjectUseCase
import org.tavioribeiro.commitic.presentation.features.main.tabs.project_tab.ProjectsViewModel


fun initKoin() {
    startKoin {
        modules(appModule)
    }
}

val appModule = module {
    // --- CAMADA DE DADOS ---
    single { ProjectLocalDataSource() }
    factory { GetProjectsUseCase(get()) }

    single<ProjectRepository> { ProjectRepositoryImpl(get()) }

    // --- CAMADA DE DOMÍNIO (UseCases) ---
    factory { SaveProjectUseCase(get()) }

    // --- CAMADA DE APRESENTAÇÃO (ViewModels) ---
    factory { ProjectsViewModel(get(), get()) }
}