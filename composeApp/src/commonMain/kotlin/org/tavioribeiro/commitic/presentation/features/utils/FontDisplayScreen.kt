package org.tavioribeiro.commitc.presentation.features.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.tavioribeiro.commitc.theme.FigtreeFontFamily

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

        fontWeights.forEach { (weight, name) ->
            item {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = weight
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "The quick brown fox jumps over the lazy dog. (Normal)",
                    fontFamily = figtreeFamily,
                    fontWeight = weight,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.bodyLarge // Baseado no estilo bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "The quick brown fox jumps over the lazy dog. (Italic)",
                    fontFamily = figtreeFamily,
                    fontWeight = weight,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
            }
        }
    }
}
