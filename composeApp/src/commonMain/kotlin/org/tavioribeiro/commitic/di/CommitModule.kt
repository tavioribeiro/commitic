package org.tavioribeiro.commitic.di

import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.CommitLocalDataSource
import org.tavioribeiro.commitic.data.repository.CommitRepositoryImpl
import org.tavioribeiro.commitic.db.CommitSchemaQueries
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.usecase.commit.DeleteCommitUseCase
import org.tavioribeiro.commitic.domain.usecase.commit.GenerateCommitUseCase
import org.tavioribeiro.commitic.domain.usecase.commit.GetCommitsUseCase
import org.tavioribeiro.commitic.domain.usecase.commit.SaveCommitUseCase
import org.tavioribeiro.commitic.presentation.features.main.tabs.commits_tab.CommitsTabViewModel

val commitModule = module {
    // data
    single { CommitSchemaQueries(driver = get()) }
    single { CommitLocalDataSource(get()) }
    single<CommitRepository> { CommitRepositoryImpl(get()) }

    // domain
    factory { SaveCommitUseCase(get()) }
    factory { GetCommitsUseCase(get()) }
    factory { DeleteCommitUseCase(get()) }
    factory { GenerateCommitUseCase(get(), get()) }

    // presentation
    factory {
        CommitsTabViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
