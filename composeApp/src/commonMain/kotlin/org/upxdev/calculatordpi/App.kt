package org.tavioribeiro.calculatordpi

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tavioribeiro.calculatordpi.presentation.features.main.CalculatorScreen
import org.tavioribeiro.calculatordpi.theme.MyAppTheme


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
