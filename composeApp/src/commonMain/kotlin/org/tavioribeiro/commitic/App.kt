package org.tavioribeiro.commitic

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tavioribeiro.commitic.presentation.features.main.CalculatorScreen
import org.tavioribeiro.commitic.presentation.features.utils.FontDisplayScreen
import org.tavioribeiro.commitic.theme.MyAppTheme


@Composable
@Preview
fun App() {
    MyAppTheme {
        CalculatorScreen()
        //FontDisplayScreen()
    }



    /*MaterialTheme(
        typography = FigtreeTypography()
    ) {
        CalculatorScreen()
        //FontDisplayScreen()
    }*/
}
