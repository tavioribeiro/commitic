package org.tavioribeiro.commitic.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.CommitLocalDataSource
import org.tavioribeiro.commitic.data.datasource.local.ConsoleDataSource
import org.tavioribeiro.commitic.data.repository.ConsoleRepositoryImpl
import org.tavioribeiro.commitic.domain.repository.ConsoleRepository
import org.tavioribeiro.commitic.domain.usecase.console.ExecuteCommandUseCase
import org.tavioribeiro.commitic.data.datasource.local.LlmLocalDataSource
import org.tavioribeiro.commitic.data.datasource.local.ProjectLocalDataSource
import org.tavioribeiro.commitic.data.datasource.remote.LlmRemoteDataSource
import org.tavioribeiro.commitic.data.repository.CommitRepositoryImpl
import org.tavioribeiro.commitic.data.repository.FileSystemRepositoryImpl
import org.tavioribeiro.commitic.data.repository.LlmRepositoryImpl
import org.tavioribeiro.commitic.data.repository.ProjectRepositoryImpl
import org.tavioribeiro.commitic.db.CommitSchemaQueries
import org.tavioribeiro.commitic.db.LlmSchemaQueries
import org.tavioribeiro.commitic.db.ProjectSchemaQueries
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.repository.FileSystemRepository
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.usecase.commit.DeleteCommitUseCase
import org.tavioribeiro.commitic.domain.usecase.commit.GenerateCommitUseCase
import org.tavioribeiro.commitic.domain.usecase.commit.GetCommitsUseCase
import org.tavioribeiro.commitic.domain.usecase.commit.SaveCommitUseCase
import org.tavioribeiro.commitic.domain.usecase.directory.CheckDirectoryExistsUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.DeleteLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.GetLlmsUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.SaveLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.project.DeleteProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.project.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.usecase.project.SaveProjectUseCase
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.features.main.tabs.commits_tab.CommitsTabViewModel
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

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }
        }
    }

    single { ProjectSchemaQueries(driver = get()) }
    single { ProjectLocalDataSource(get()) }
    single <ProjectRepository> {
        ProjectRepositoryImpl(
            get()
        )
    }

    single { LlmSchemaQueries(driver = get()) }
    single { LlmLocalDataSource(get()) }
    single <LlmRepository> {
        LlmRepositoryImpl(
            get(),
            get()
        )
    }

    single { CommitSchemaQueries(driver = get()) }
    single { CommitLocalDataSource(get()) }
    single { LlmRemoteDataSource(get()) }
    single <CommitRepository> {
        CommitRepositoryImpl(
            get()
        )
    }



    single { ConsoleDataSource() }
    single <ConsoleRepository> { ConsoleRepositoryImpl(get()) }

    single <FileSystemRepository> { FileSystemRepositoryImpl() }
}

val domainModule = module {
    factory { SaveProjectUseCase(get(), get()) }
    factory { GetProjectsUseCase(get()) }
    factory { DeleteProjectUseCase(get()) }

    factory { SaveLlmUseCase(get()) }
    factory { GetLlmsUseCase(get()) }
    factory { DeleteLlmUseCase(get()) }

    factory { SaveCommitUseCase(get()) }
    factory { GetCommitsUseCase(get()) }
    factory { DeleteCommitUseCase(get()) }
    factory { GenerateCommitUseCase(get(), get()) }

    factory { ExecuteCommandUseCase(get()) }

    factory { ExecuteCommandUseCase(get()) }
}

val presentationModule = module {
    single { ToastViewModel() }

    factory {
        ProjectsTabViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    factory {
        LlmsTabViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }



    factory {
        CommitsTabViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}