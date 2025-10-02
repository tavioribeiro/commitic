package org.tavioribeiro.commitic.presentation.components.select

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    initialOption: SelectOptionModel? = null, //1°
    initialPosition: Int? = null,  //2°
    initialValue: String? = null, //3°
    onValueChange: (String) -> Unit,
    isBackgroudColorDark: Boolean = false,
    emptyStateText: String = "Não há opções disponíveis"
) {
    //println(initialOption)
    val isEmpty = options.isEmpty()
    var expanded by remember { mutableStateOf(false) }
    var selectedLabel by remember { mutableStateOf("") }

    LaunchedEffect(initialValue, initialOption, initialPosition, options) {
        if (!isEmpty) {
            val label = initialOption?.label
                ?: options.getOrNull(initialPosition ?: -1)?.label
                ?: options.find { it.value == initialValue }?.label
            selectedLabel = label ?: ""
        }
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
            onExpandedChange = { if (!isEmpty) expanded = !expanded }
        ) {
            val placeholderColor = if (isBackgroudColorDark) AppTheme.colors.color6 else AppTheme.colors.color6

            OutlinedTextField(
                value = if (isEmpty) emptyStateText else selectedLabel,
                onValueChange = {},
                readOnly = true,
                enabled = !isEmpty,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = placeholderColor
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
                    disabledTrailingIconColor = if (isBackgroudColorDark) AppTheme.colors.onColor3 else AppTheme.colors.color3,
                    disabledTextColor = placeholderColor,
                    disabledBorderColor = if (isBackgroudColorDark) AppTheme.colors.onColor3 else AppTheme.colors.color3,
                )
            )

            if (!isEmpty) {
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
}