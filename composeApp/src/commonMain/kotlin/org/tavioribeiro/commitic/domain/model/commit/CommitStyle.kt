package org.tavioribeiro.commitic.domain.model.commit

enum class CommitStyle(val displayName: String, val instruction: String) {
    PROFISSIONAL("Profissional", "Use a professional and formal tone. Do not use emojis or emoticons. Commit format: {title (max 72 characters)}\\n\\n{optional body with detailed description}"),
    CASUAL("Casual/Descontraído", "Use a casual and relaxed tone with lighter language. The FIRST character of the title MUST be the emoji corresponding to the CHANGE CATEGORY, using EXACTLY this table: FEATURE -> ✨, REFACTOR -> 🎨, STYLE -> 🎨, BUGFIX -> 🐛, CHORE -> 🛠️, DOCS -> 📝, PERFORMANCE -> ⚡️, TEST -> ✅. Do NOT use any other emoji. After the colon (:), the first letter MUST be uppercase. Commit format: {emoji} {type}: {Title}\n\nDescription:\n- bullet point 1\n- bullet point 2\nEach bullet point must describe a specific change clearly and in detail."),
    TECNICO("Técnico/Detalhado", "Be extremely technical and detailed, focusing on implementation aspects. Do not use emojis or emoticons."),
    MINIMALISTA("Minimalista", "Be minimalist and concise, only the essentials. Do not use emojis or emoticons. Only a single summary line, no body.")
}
