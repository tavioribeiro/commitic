package org.tavioribeiro.commitic

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.tavioribeiro.commitic.di.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    initKoin()

    ComposeViewport(document.body!!) {
        App()
    }
}