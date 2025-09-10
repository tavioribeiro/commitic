package org.tavioribeiro.commitic.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.LlmLocalDataSource
import org.tavioribeiro.commitic.data.datasource.local.ProjectLocalDataSource
import org.tavioribeiro.commitic.data.repository.LlmRepositoryImpl
import org.tavioribeiro.commitic.data.repository.ProjectRepositoryImpl
import org.tavioribeiro.commitic.db.LlmSchemaQueries
import org.tavioribeiro.commitic.db.ProjectSchemaQueries
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.usecase.llm.DeleteLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.GetLlmsUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.SaveLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.project.DeleteProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.project.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.usecase.project.SaveProjectUseCase
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.LlmsTabViewModel
import org.tavioribeiro.commitic.presentation.features.main.tabs.projects_tab.ProjectsTabViewModel


expect val platformModule: Module

fun initKoin() {
    startKoin {
        modules(
            platformModule,
            dataModule,
            domainModule,
            presentationModule
        )
    }
}

val dataModule = module {
    single { ProjectSchemaQueries(driver = get()) }
    single { ProjectLocalDataSource(get()) }
    single<ProjectRepository> {
        ProjectRepositoryImpl(
            get()
        )
    }


    single { LlmSchemaQueries(driver = get()) }
    single { LlmLocalDataSource(get()) }
    single<LlmRepository> {
        LlmRepositoryImpl(
            get()
        )
    }
}

val domainModule = module {
    factory { SaveProjectUseCase(get()) }
    factory { GetProjectsUseCase(get()) }
    factory { DeleteProjectUseCase(get()) }


    factory { SaveLlmUseCase(get()) }
    factory { GetLlmsUseCase(get()) }
    factory { DeleteLlmUseCase(get()) }
}

val presentationModule = module {
    single { ToastViewModel() }

    factory {
        ProjectsTabViewModel(
        get(),
        get(),
        get(),
        get())
    }


    factory {
        LlmsTabViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}