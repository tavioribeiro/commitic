package org.tavioribeiro.commitic

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import commitic.composeapp.generated.resources.Res
import commitic.composeapp.generated.resources.logo3
import org.jetbrains.compose.resources.painterResource
import java.awt.Dimension // Importe novamente a classe Dimension

fun main() = application {

    val windowInitialState = rememberWindowState(
        width = 1440.dp,
        height = 804.dp
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Commitic",
        icon = painterResource(Res.drawable.logo3),
        state = windowInitialState,
        //resizable = false
    ) {
        window.minimumSize = Dimension(600, 700)
        //window.maximumSize = Dimension(1200, 900)

        App()
    }
}