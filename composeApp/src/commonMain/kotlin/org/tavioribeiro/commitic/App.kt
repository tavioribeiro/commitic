package org.tavioribeiro.commitic

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.tavioribeiro.commitic.presentation.components.toast.ToastHost
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.features.main.CalculatorScreen
import org.tavioribeiro.commitic.theme.MyAppTheme


@Composable
@Preview
fun App() {
    val toastViewModel: ToastViewModel = koinInject()

    MyAppTheme {
        CalculatorScreen()
        //FontDisplayScreen()
        ToastHost(viewModel = toastViewModel)
    }
}
