package org.tavioribeiro.commitic.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import java.io.File
import javax.swing.JFileChooser
import javax.swing.UIManager
import javax.swing.filechooser.FileSystemView

@Composable
actual fun DirectoryPicker(
    show: Boolean,
    title: String,
    onResult: (String?) -> Unit
) {
    LaunchedEffect(show) {
        if (show) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            } catch (e: Exception) {

            }

            val fileChooser = JFileChooser(FileSystemView.getFileSystemView().homeDirectory).apply {
                dialogTitle = title
                fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                isAcceptAllFileFilterUsed = false
            }

            val result = fileChooser.showOpenDialog(null)
            if (result == JFileChooser.APPROVE_OPTION) {
                val selectedFile = fileChooser.selectedFile
                var absolutePath = selectedFile.absolutePath


                val separator = File.separator
                if (absolutePath.endsWith(separator + selectedFile.name) &&
                    selectedFile.parentFile?.name == selectedFile.name) {
                    absolutePath = selectedFile.parentFile.absolutePath
                }


                onResult(absolutePath)
            } else {
                onResult(null)
            }
        }
    }
}