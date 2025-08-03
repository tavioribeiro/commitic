package org.upxdev.calculatordpi

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import calculatordpi.composeapp.generated.resources.Res
import calculatordpi.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import java.awt.Dimension // Importe novamente a classe Dimension

fun main() = application {

    val windowInitialState = rememberWindowState(
        width = 500.dp,
        height = 400.dp
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "CalculatorDPI",
        icon = painterResource(Res.drawable.logo),
        state = windowInitialState,
        resizable = false
    ) {
        //window.minimumSize = Dimension(400, 350)
        //window.maximumSize = Dimension(1200, 900)

        App()
    }
}