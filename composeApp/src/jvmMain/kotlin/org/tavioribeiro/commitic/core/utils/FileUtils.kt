package org.tavioribeiro.commitic.core.utils

import java.io.File


actual fun directoryExists(path: String): Boolean {
    val directory = File(path)


    println("sdddddddddddddddddddddddddddd ${directory.exists() && directory.isDirectory}")
    return directory.exists() && directory.isDirectory
}
