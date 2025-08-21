package org.tavioribeiro.commitic.presentation.features.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.tavioribeiro.commitic.theme.FigtreeFontFamily

@Composable
fun FontDisplayScreen() {
    val fontWeights = listOf(
        FontWeight.Light to "Light",
        FontWeight.Normal to "Normal (Regular)",
        FontWeight.Medium to "Medium",
        FontWeight.SemiBold to "SemiBold",
        FontWeight.Bold to "Bold",
        FontWeight.ExtraBold to "ExtraBold",
        FontWeight.Black to "Black"
    )

    // Lista com todos os estilos de tipografia do MaterialTheme.typography
    val typographyStyles = listOf(
        "displayLarge" to MaterialTheme.typography.displayLarge,
        "displayMedium" to MaterialTheme.typography.displayMedium,
        "displaySmall" to MaterialTheme.typography.displaySmall,
        "headlineLarge" to MaterialTheme.typography.headlineLarge,
        "headlineMedium" to MaterialTheme.typography.headlineMedium,
        "headlineSmall" to MaterialTheme.typography.headlineSmall,
        "titleLarge" to MaterialTheme.typography.titleLarge,
        "titleMedium" to MaterialTheme.typography.titleMedium,
        "titleSmall" to MaterialTheme.typography.titleSmall,
        "bodyLarge" to MaterialTheme.typography.bodyLarge,
        "bodyMedium" to MaterialTheme.typography.bodyMedium,
        "bodySmall" to MaterialTheme.typography.bodySmall,
        "labelLarge" to MaterialTheme.typography.labelLarge,
        "labelMedium" to MaterialTheme.typography.labelMedium,
        "labelSmall" to MaterialTheme.typography.labelSmall
    )

    val figtreeFamily = FigtreeFontFamily()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Demonstração da Fonte Figtree",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Itera sobre cada estilo de tipografia
        typographyStyles.forEach { (styleName, textStyle) ->
            item {
                Text(
                    text = "Estilo: $styleName",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Para cada estilo, itera sobre os pesos da fonte
            fontWeights.forEach { (weight, weightName) ->
                item {
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text = weightName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = weight
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "The quick brown fox jumps over the lazy dog. (Normal)",
                            fontFamily = figtreeFamily,
                            fontWeight = weight,
                            fontStyle = FontStyle.Normal,
                            style = textStyle.copy(fontFamily = figtreeFamily, fontWeight = weight, fontStyle = FontStyle.Normal)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "The quick brown fox jumps over the lazy dog. (Italic)",
                            fontFamily = figtreeFamily,
                            fontWeight = weight,
                            fontStyle = FontStyle.Italic,
                            style = textStyle.copy(fontFamily = figtreeFamily, fontWeight = weight, fontStyle = FontStyle.Italic)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            item {
                HorizontalDivider()
            }
        }
    }
}