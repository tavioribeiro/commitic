package org.tavioribeiro.commitic.presentation.components.select

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.tavioribeiro.commitic.presentation.model.SelectOptionModel
import org.tavioribeiro.commitic.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectInput(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    options: List<SelectOptionModel>,
    initialValue: String? = null,
    onValueChange: (String) -> Unit,
    isBackgroudColorDark: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    val initialLabel = options.find { it.value == initialValue }?.label ?: ""
    var selectedLabel by remember { mutableStateOf(initialLabel) }

    LaunchedEffect(initialValue, options) {
        selectedLabel = options.find { it.value == initialValue }?.label ?: ""
    }

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

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedLabel,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = if (isBackgroudColorDark) AppTheme.colors.color6 else AppTheme.colors.color6
                    )
                },
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
                    unfocusedTextColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
                    unfocusedBorderColor = if (isBackgroudColorDark) AppTheme.colors.onColor3 else AppTheme.colors.color3,
                    focusedBorderColor = AppTheme.colors.color7,
                    focusedTrailingIconColor = AppTheme.colors.color7,
                    unfocusedTrailingIconColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
                    disabledTrailingIconColor = if (isBackgroudColorDark) AppTheme.colors.onColor3 else AppTheme.colors.color3
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption.label) },
                        onClick = {
                            selectedLabel = selectionOption.label
                            onValueChange(selectionOption.value)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
