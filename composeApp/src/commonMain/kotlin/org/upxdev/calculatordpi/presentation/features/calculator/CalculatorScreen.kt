package org.upxdev.calculatordpi.presentation.features.calculator


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.upxdev.calculatordpi.presentation.components.buttons.ActionButton
import org.upxdev.calculatordpi.theme.AppTheme
import org.upxdev.calculatordpi.theme.ThemeState

@Composable
fun CalculatorScreen() {
    Column(
        modifier = Modifier
            .background(AppTheme.colors.color1)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Text(
            text = "Calculadora de DPI",
            color = AppTheme.colors.onColor1,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 36.dp)
        )
        ActionButton(
            text = "Clicarrrr",
            onClick = {
                ThemeState.toggleTheme()
            }
        )
    }
}
