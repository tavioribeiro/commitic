package org.tavioribeiro.commitic

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import commitic.composeapp.generated.resources.Res
import commitic.composeapp.generated.resources.commitic_icon_l
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.di.initKoin
import java.awt.Dimension


fun main() {

    application {
        initKoin()

        val windowInitialState = rememberWindowState(
            width = 1440.dp,
            height = 804.dp
        )

        Window(
            onCloseRequest = ::exitApplication,
            title = "Commitic",
            icon = painterResource(Res.drawable.commitic_icon_l),
            state = windowInitialState,
        ) {
            window.minimumSize = Dimension(903, 700)
            App()
        }
    }
}