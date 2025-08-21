package org.tavioribeiro.commitc.presentation.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitc.theme.AppTheme

@Composable
fun IconTextButton(
    text: String,
    onClick: () -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.color7,
            contentColor = AppTheme.colors.onColor7
        ),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement  = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically ,
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = AppTheme.colors.onColor7,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = text,
                color = AppTheme.colors.onColor7,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}