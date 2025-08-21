package org.tavioribeiro.commitc.general_utils

import androidx.compose.runtime.Composable

@Composable
actual fun DirectoryPicker (
    show: Boolean,
    title: String,
    onResult: (String?) -> Unit
) {
    if (show) {
        onResult(null)
    }
}