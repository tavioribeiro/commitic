package org.tavioribeiro.commitic.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.browser.window
import org.w3c.dom.events.Event

@Composable
private fun rememberWindowSize(): State<Pair<Int, Int>> {
    val windowSize = produceState(initialValue = window.innerWidth to window.innerHeight) {
        val handler = { _: Event ->
            value = window.innerWidth to window.innerHeight
        }

        window.addEventListener("resize", handler)

        awaitDispose {
            window.removeEventListener("resize", handler)
        }
    }
    return windowSize
}

@Composable
actual fun getScreenWidthDp(): Dp {
    val widthInPixels = rememberWindowSize().value.first
    return widthInPixels.dp
}

@Composable
actual fun getScreenHeightDp(): Dp {
    val heightInPixels = rememberWindowSize().value.second
    return heightInPixels.dp
}