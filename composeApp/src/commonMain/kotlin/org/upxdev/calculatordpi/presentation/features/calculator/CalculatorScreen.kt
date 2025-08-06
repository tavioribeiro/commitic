package org.upxdev.calculatordpi.presentation.features.calculator


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.upxdev.calculatordpi.presentation.components.buttons.ActionButton
import org.upxdev.calculatordpi.presentation.components.inputs.MidInput
import org.upxdev.calculatordpi.theme.AppTheme
import org.upxdev.calculatordpi.theme.ThemeState
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import calculatordpi.composeapp.generated.resources.Res
import calculatordpi.composeapp.generated.resources.icon_clear
import calculatordpi.composeapp.generated.resources.icon_plus
import org.jetbrains.compose.resources.painterResource
import org.upxdev.calculatordpi.presentation.components.buttons.IconTextButton


@Composable
fun CalculatorScreen() {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(AppTheme.colors.color1)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Row(
            Modifier.height(73.dp)
                .fillMaxWidth()
                .background(AppTheme.colors.color2)
                .padding(horizontal = 80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(Res.drawable.icon_clear),
                    contentDescription = "Meu Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(32.dp)
                )

                Text(
                    text = "CalculatorDPI",
                    color = AppTheme.colors.onColor1,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            IconTextButton(
                text = "Novo Projeto",
                onClick = {  },
                icon = painterResource(Res.drawable.icon_plus),
                modifier = Modifier.padding(16.dp)
            )
        }

        MidInput(
            initialValue = text,
            onValueChange = { newText ->
                text = newText
            },
            placeholderText = "Digite algo..."
        )


        ActionButton(
            text = "Clicarrrr",
            onClick = {
                ThemeState.toggleTheme()
            }
        )
    }
}
