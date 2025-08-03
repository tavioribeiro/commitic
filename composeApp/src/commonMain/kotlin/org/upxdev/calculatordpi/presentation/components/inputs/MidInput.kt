package org.upxdev.calculatordpi.presentation.components.inputs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import org.upxdev.calculatordpi.theme.AppTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MidInput(
    initialValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
) {
    var typedText by remember { mutableStateOf(initialValue)}

    TextField(
        value = typedText,
        onValueChange = {
            typedText = it
            onValueChange(it)
        },
        modifier = modifier
            .border(
                width = 1.dp,
                color = AppTheme.colors.color4,
                shape = RoundedCornerShape(8.dp)
            ),
        placeholder = { Text(text = placeholderText, color = AppTheme.colors.color6) },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = AppTheme.colors.onColor1,
            unfocusedTextColor = AppTheme.colors.onColor1,
            cursorColor = AppTheme.colors.onColor1,
            focusedContainerColor = AppTheme.colors.color2,
            unfocusedContainerColor = AppTheme.colors.color2,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences
        )
    )
}