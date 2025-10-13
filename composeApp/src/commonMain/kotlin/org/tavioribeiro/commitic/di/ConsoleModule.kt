package org.tavioribeiro.commitic.di

import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.ConsoleDataSource
import org.tavioribeiro.commitic.data.repository.ConsoleRepositoryImpl
import org.tavioribeiro.commitic.domain.repository.ConsoleRepository
import org.tavioribeiro.commitic.domain.usecase.console.ExecuteCommandUseCase

val consoleModule = module {
    // data
    single { ConsoleDataSource() }
    single<ConsoleRepository> { ConsoleRepositoryImpl(get()) }

    // domain
    factory { ExecuteCommandUseCase(get()) }
}
