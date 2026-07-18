package org.tavioribeiro.commitic.domain.model.commit

enum class CommitStyle(val displayName: String, val instruction: String) {
    PROFISSIONAL("Profissional", "Use um tom profissional e formal."),
    CASUAL("Casual/Descontraído", "Use um tom casual e descontraído, com linguagem mais leve."),
    TECNICO("Técnico/Detalhado", "Seja extremamente técnico e detalhado, focando em aspectos de implementação."),
    MINIMALISTA("Minimalista", "Seja minimalista e conciso, apenas o essencial.")
}
