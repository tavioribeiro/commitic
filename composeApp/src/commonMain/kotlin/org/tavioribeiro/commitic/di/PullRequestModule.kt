package org.tavioribeiro.commitic.di

import org.koin.dsl.module

/*val pullRequest = module {
    // data
    single { PullRequestSchemaQueries(driver = get()) }
    single { PullRequestLocalDataSource(get()) }
    single<PullRequestRepository> { PullRequestRepositoryImpl(get()) }

    // domain
    factory { SavePullRequestUseCase(get()) }
    factory { GetPullRequestsUseCase(get()) }
    factory { DeletePullRequestUseCase(get()) }
    factory { GeneratePullRequestUseCase(get(), get(), get()) }

    // presentation
    factory {
        PullRequestsTabViewModel(
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
}*/
