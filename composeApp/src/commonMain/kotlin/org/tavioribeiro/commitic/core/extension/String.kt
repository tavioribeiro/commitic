package org.tavioribeiro.commitic.core.extension


fun String.maskStart(prefixLength: Int = 5): String {
    return if (this.length <= prefixLength) {
        this
    } else {
        val prefix = this.substring(0, prefixLength)
        val asterisks = "*".repeat(this.length - prefixLength)
        prefix + asterisks
    }
}


fun String.toSingleLine(): String {
    return this.replace(Regex("(\\r\\n|\\n|\\r)"), " ").trim()
}