package org.upxdev.calculatordpi

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import calculatordpi.composeapp.generated.resources.Res
import calculatordpi.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CalculatorDPI",
        icon = painterResource(Res.drawable.logo)
    ) {
        App()
    }
}