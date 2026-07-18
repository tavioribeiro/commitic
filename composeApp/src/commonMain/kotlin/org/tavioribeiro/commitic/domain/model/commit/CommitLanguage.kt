package org.tavioribeiro.commitic.domain.model.commit

enum class CommitLanguage(val displayName: String, val instruction: String) {
    PORTUGUES("Português", "Write/think in Brazilian Portuguese."),
    INGLES("English", "Write/think in English."),
    ESPANHOL("Español", "Write/think in Spanish.")
}
