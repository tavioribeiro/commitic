package org.tavioribeiro.commitic.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

expect val platformModule: Module

fun initKoin() {
    startKoin {
        modules(
            platformModule,
            dataModule,
            domainModule,
            presentationModule,
            projectModule,
            llmModule,
            commitModule,
            consoleModule,
            preferencesModule,
            pullrequestModule
        )
    }
}
