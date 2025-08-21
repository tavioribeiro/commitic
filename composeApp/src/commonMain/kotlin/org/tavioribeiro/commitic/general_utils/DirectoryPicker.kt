package org.tavioribeiro.commitc.general_utils

import androidx.compose.runtime.Composable

@Composable
expect fun DirectoryPicker(
    show: Boolean,
    title: String,
    onResult: (String?) -> Unit
)