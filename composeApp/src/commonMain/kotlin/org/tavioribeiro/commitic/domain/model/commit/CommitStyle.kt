package org.tavioribeiro.commitic.domain.model.commit

enum class CommitStyle(val displayName: String, val instruction: String) {
    PROFISSIONAL("Profissional", "Use um tom profissional e formal. Não use emojis ou emoticons. Formato do commit: {título (máx 72 caracteres)}\\n\\n{corpo opcional com descrição detalhada}"),
    CASUAL("Casual/Descontraído", "Use um tom casual e descontraído, com linguagem mais leve. O PRIMEIRO caractere do título DEVE ser o emoji correspondente à CATEGORIA da mudança, usando EXATAMENTE esta tabela: FEATURE -> ✨, REFACTOR -> 🎨, STYLE -> 🎨, BUGFIX -> 🐛, CHORE -> 🛠️, DOCS -> 📝, PERFORMANCE -> ⚡️, TEST -> ✅. NÃO use nenhum outro emoji. Após os dois-pontos (:), a primeira letra DEVE ser maiúscula. Formato do commit: {emoji} {tipo}: {Título}\n\nDescrição:\n- bullet point 1\n- bullet point 2\nCada bullet point deve descrever uma mudança específica de forma clara e detalhada."),
    TECNICO("Técnico/Detalhado", "Seja extremamente técnico e detalhado, focando em aspectos de implementação. Não use emojis ou emoticons."),
    MINIMALISTA("Minimalista", "Seja minimalista e conciso, apenas o essencial. Não use emojis ou emoticons. Apenas uma única linha de resumo, sem corpo.")
}
