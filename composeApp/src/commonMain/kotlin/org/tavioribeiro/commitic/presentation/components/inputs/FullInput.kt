package org.tavioribeiro.commitc.presentation.components.inputs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.tavioribeiro.commitc.theme.AppTheme

@Composable
fun FullInput(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    initialValue: String = "",
    onValueChange: (String) -> Unit,
    isBackgroudColorDark: Boolean = false
) {
    var text by remember { mutableStateOf(initialValue) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Text(
            text = title,
            color = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = if (isBackgroudColorDark) AppTheme.colors.color6 else AppTheme.colors.color6
                )
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
                unfocusedTextColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
                unfocusedBorderColor = if (isBackgroudColorDark) AppTheme.colors.onColor3 else AppTheme.colors.color3,
                focusedBorderColor = AppTheme.colors.color7
            )
        )

        Text(
            //text = title,
            text = "",
            color = AppTheme.colors.color10,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}