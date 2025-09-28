package org.tavioribeiro.commitic.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.tavioribeiro.commitic.data.repository.FileSystemRepositoryImpl
import org.tavioribeiro.commitic.domain.repository.FileSystemRepository

val dataModule = module {
    // httpClient
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

    // file System
    single<FileSystemRepository> { FileSystemRepositoryImpl() }
}
