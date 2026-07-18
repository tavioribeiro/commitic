package org.tavioribeiro.commitic.domain.model.commit

enum class CommitLanguage(val displayName: String, val instruction: String) {
    PORTUGUES("Português", "Escreva/pense em Português Brasileiro."),
    INGLES("English", "Write/think in English."),
    ESPANHOL("Español", "Escriba/piensa en Español.")
}
