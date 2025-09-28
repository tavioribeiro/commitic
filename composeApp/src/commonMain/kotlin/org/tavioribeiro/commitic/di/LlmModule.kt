package org.tavioribeiro.commitic.di

import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.LlmLocalDataSource
import org.tavioribeiro.commitic.data.datasource.remote.LlmRemoteDataSource
import org.tavioribeiro.commitic.data.repository.LlmRepositoryImpl
import org.tavioribeiro.commitic.db.LlmSchemaQueries
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.usecase.llm.DeleteLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.GetLlmsUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.SaveLlmUseCase
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.LlmsTabViewModel

val llmModule = module {
    // data
    single { LlmSchemaQueries(driver = get()) }
    single { LlmLocalDataSource(get()) }
    single { LlmRemoteDataSource(get()) }
    single<LlmRepository> { LlmRepositoryImpl(get(), get()) }

    // domain
    factory { SaveLlmUseCase(get()) }
    factory { GetLlmsUseCase(get()) }
    factory { DeleteLlmUseCase(get()) }

    // presentation
    factory {
        LlmsTabViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}
