package org.tavioribeiro.commitic.core.utils

import java.io.File


actual fun directoryExists(path: String): Boolean {
    val directory = File(path)

    return directory.exists() && directory.isDirectory
}
