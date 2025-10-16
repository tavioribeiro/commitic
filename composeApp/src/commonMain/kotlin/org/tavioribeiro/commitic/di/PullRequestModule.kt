package org.tavioribeiro.commitic.di

import org.koin.dsl.factory
import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.CommitLocalDataSource
import org.tavioribeiro.commitic.data.repository.CommitRepositoryImpl
import org.tavioribeiro.commitic.db.CommitSchemaQueries
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.usecase.commit.GetCommitsByprojectIdAndBranchUseCase
import org.tavioribeiro.commitic.domain.usecase.pull_request.GeneratePullRequestUseCase
import org.tavioribeiro.commitic.presentation.features.main.tabs.pull_request.PullRequestTabViewModel


val pullrequestModule = module {
    // data
    single { CommitSchemaQueries(driver = get()) }
    single { CommitLocalDataSource(get()) }
    single<CommitRepository> { CommitRepositoryImpl(get()) }

    // domain
    factory { GeneratePullRequestUseCase(
        get(),
        get(),
        get(),
        get()
    )}

    factory { GetCommitsByprojectIdAndBranchUseCase(
        get(),
        get(),
        get()
    )}


    // presentation
    factory {
        PullRequestTabViewModel(
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
