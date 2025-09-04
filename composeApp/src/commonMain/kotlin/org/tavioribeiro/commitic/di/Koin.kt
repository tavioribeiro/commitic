package org.tavioribeiro.commitic.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.ProjectLocalDataSource
import org.tavioribeiro.commitic.data.repository.ProjectRepositoryImpl
import org.tavioribeiro.commitic.db.ProjectSchemaQueries
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.usecase.project.DeleteProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.project.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.usecase.project.SaveProjectUseCase
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.features.main.tabs.project_tab.ProjectsViewModel


fun initKoin() {
    startKoin {
        modules(
            dataModule,
            domainModule,
            presentationModule
        )
    }
}

val dataModule = module {
    single <SqlDriver> { JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY) }
    single { ProjectSchemaQueries(driver = get())}
    single { ProjectLocalDataSource(get()) }

    single<ProjectRepository> { ProjectRepositoryImpl(get()) }
}

val domainModule = module {
    factory { SaveProjectUseCase(get()) }
    factory { GetProjectsUseCase(get()) }
    factory { DeleteProjectUseCase(get()) }
}

val presentationModule = module {
    single { ToastViewModel() }
    factory { ProjectsViewModel(get(), get(), get(), get()) }
}