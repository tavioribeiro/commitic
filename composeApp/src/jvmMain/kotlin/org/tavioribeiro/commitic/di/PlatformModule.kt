package org.tavioribeiro.commitic.di

import app.cash.sqldelight.db.SqlDriver
import org.koin.dsl.module
import org.tavioribeiro.commitic.core.database.DatabaseDriverFactory
import org.koin.core.module.Module

actual val platformModule: Module = module {
    single { DatabaseDriverFactory() }
    single<SqlDriver> { get<DatabaseDriverFactory>().createDriver() }
}