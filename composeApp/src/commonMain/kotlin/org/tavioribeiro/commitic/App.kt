package org.tavioribeiro.commitc

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tavioribeiro.commitc.presentation.features.main.CalculatorScreen
import org.tavioribeiro.commitc.theme.MyAppTheme


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
