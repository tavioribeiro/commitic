package org.tavioribeiro.commitic.core.utils

import androidx.compose.runtime.Composable

@Composable
expect fun DirectoryPicker(
    show: Boolean,
    title: String,
    onResult: (String?) -> Unit
)