package org.tavioribeiro.commitic.di

import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.ProjectLocalDataSource
import org.tavioribeiro.commitic.data.repository.ProjectRepositoryImpl
import org.tavioribeiro.commitic.db.ProjectSchemaQueries
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.usecase.project.DeleteProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.project.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.usecase.project.SaveProjectUseCase
import org.tavioribeiro.commitic.presentation.features.main.tabs.projects_tab.ProjectsTabViewModel

val projectModule = module {
    // data
    single { ProjectSchemaQueries(driver = get()) }
    single { ProjectLocalDataSource(get()) }
    single<ProjectRepository> { ProjectRepositoryImpl(get()) }

    // domain
    factory { SaveProjectUseCase(get(), get(), get()) }
    factory { GetProjectsUseCase(get()) }
    factory { DeleteProjectUseCase(get()) }

    // presentation
    factory {
        ProjectsTabViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
