package org.tavioribeiro.commitic.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module
import org.tavioribeiro.commitic.data.datasource.local.PreferencesLocalDataSource
import org.tavioribeiro.commitic.data.repository.PreferencesRepositoryImpl
import org.tavioribeiro.commitic.domain.repository.PreferencesRepository
import org.tavioribeiro.commitic.domain.usecase.preferences.GetCommitLanguageUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.GetCommitStyleUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.GetSelectedDelayBetweenStepsUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.GetSelectedLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.GetSelectedProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.SaveCommitLanguageUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.SaveCommitStyleUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.SaveSelectedDelayBetweenStepsUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.SaveSelectedLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.SaveSelectedProjectUseCase

val preferencesModule = module {
    factory { Settings() }

    // data
    single { PreferencesLocalDataSource(settings = get()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(localDataSource = get()) }

    // domain
    factory { SaveSelectedProjectUseCase(preferencesRepository = get()) }
    factory { GetSelectedProjectUseCase(preferencesRepository = get()) }
    factory { SaveSelectedLlmUseCase(preferencesRepository = get()) }
    factory { GetSelectedLlmUseCase(preferencesRepository = get()) }
    factory { SaveSelectedDelayBetweenStepsUseCase(preferencesRepository = get()) }
    factory { GetSelectedDelayBetweenStepsUseCase(preferencesRepository = get()) }
    factory { SaveCommitLanguageUseCase(preferencesRepository = get()) }
    factory { GetCommitLanguageUseCase(preferencesRepository = get()) }
    factory { SaveCommitStyleUseCase(preferencesRepository = get()) }
    factory { GetCommitStyleUseCase(preferencesRepository = get()) }
}