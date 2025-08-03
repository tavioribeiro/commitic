package org.upxdev.calculatordpi

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.upxdev.calculatordpi.presentation.features.calculator.CalculatorScreen
import org.upxdev.calculatordpi.presentation.features.utils.FontDisplayScreen
import org.upxdev.calculatordpi.theme.FigtreeTypography
import org.upxdev.calculatordpi.theme.MyAppTheme


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
