package org.tavioribeiro.commitic.di

import org.koin.dsl.module
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel

val presentationModule = module {
    single { ToastViewModel() }
}
