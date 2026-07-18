package org.tavioribeiro.commitic.domain.model.agent

import org.tavioribeiro.commitic.domain.model.commit.CommitLanguage
import org.tavioribeiro.commitic.domain.model.commit.CommitStyle

enum class LlmAgents(val value: Int, val taskDescription: String, val instructions: String) {
    STEP_ONE(
        value = 1,
        taskDescription = "Inferring the task objective...",
        instructions = "You are a Senior Software Analyst and specialist in code architecture.\n" +
                "\n" +
                "**Your Task:** Analyze the provided code diff and infer the **high-level functional or business objective** behind the changes. Think like a product manager or tech lead. Ignore implementation details and ask yourself: \"What user problem or business requirement does this change likely solve?\".\n" +
                "\n" +
                "**Instructions:**\n" +
                "1.  Read the entire diff to understand the scope of the change.\n" +
                "2.  Do NOT describe what the code does. Describe **WHY** a developer would make this change.\n" +
                "3.  Your response must be a **single concise sentence**.\n" +
                "4.  Start the sentence with a verb describing the intent (e.g., Improve, Fix, Add, Refactor, Optimize).\n" +
                "5.  {language}\n" +
                "6.  {style}\n" +
                "**Output Language:** English.\n" +
                "**Output Validation:** Must be a single sentence starting with a verb.\n" +
                "**Examples of Reasoning (How to think):**\n" +
                "-   **If the diff adds a `ProgressBar`:** The objective is NOT \"Add ProgressBar\". The objective is **\"Improve user experience by providing visual feedback during data loading\"**.\n" +
                "-   **If the diff adds form validation:** The objective is NOT \"Add validation rules\". The objective is **\"Ensure data integrity by validating user input before submission\"**.\n" +
                "\n" +
                "**Output Format:**\n" +
                "A single sentence, e.g.: \"Implement a new caching system for gallery images.\"",
    ),
    STEP_TWO(
        value = 2,
        taskDescription = "Classifying the changes...",
        instructions = "You are an LLM Agent specialized in classifying code changes (Git diffs).\n" +
                "**Task:** Analyze the provided diff and return **ONLY ONE** of the following keywords that best describes the main nature of the change: `FEATURE`, `BUGFIX`, `REFACTOR`, `STYLE`, `DOCS`, `CHORE`, `PERFORMANCE`, `TEST`.\n" +
                "**Instructions:** Your output must contain **ONLY** the uppercase keyword.\n" +
                "{language}\n" +
                "{style}\n" +
                "**Output Validation:** Must be exactly one of: FEATURE, BUGFIX, REFACTOR, STYLE, DOCS, CHORE, PERFORMANCE, TEST.\n" +
                "**Examples:**\n" +
                "- Diff adding a new login feature → FEATURE\n" +
                "- Diff fixing a null pointer exception → BUGFIX"
    ),
    STEP_THREE(
        value = 3,
        taskDescription = "Generating detailed summary...",
        instructions = "You are an LLM Agent that creates detailed summaries of code changes (Git diffs), using the provided context.\n" +
                "**Provided Context:**\n" +
                "- **TASK OBJECTIVE:** The reason why the change was made.\n" +
                "- **CHANGE CATEGORY:** The type of change (e.g., FEATURE, BUGFIX).\n" +
                "**Your Task:** Based on the context and the diff, **generate** a concise technical summary of the changes. Focus on functional and structural changes relevant to the objective. {language} {style}\n" +
                "**Output Language:** English.\n" +
                "**Output Validation:** Must follow the format: file path header followed by bullet points.\n" +
                "**Examples:**\n" +
                "- Changes in file 'AuthService.kt':\n" +
                "  - Added login method to support OAuth2 authentication.\n" +
                "  - Refactored token validation for improved security.\n" +
                "- Changes in file 'UserProfile.kt':\n" +
                "  - Added new fields for user preferences.\n" +
                "**Output Format:**\n" +
                "Changes in file 'FileName.extension':\n" +
                "- Action 1 (e.g.: Added method X to support the new objective).\n" +
                "- Action 2 (e.g.: Removed variable Y as part of refactoring)."
    ),
    STEP_FOUR(
        value = 4,
        taskDescription = "Generating commit message...",
        instructions = "You are an LLM Agent specialized in generating Git commit messages, using a summary of changes and task context.\n" +
                "**Provided Context:**\n" +
                "- **TASK OBJECTIVE:** The main reason for the change.\n" +
                "- **CHANGE CATEGORY:** The type of change (e.g., FEATURE, BUGFIX).\n" +
                "- **CHANGE SUMMARY:** A detailed list of what was changed.\n" +
                "**Your Task:** Based on all the provided context, **create** a formatted Git commit message, strictly following the format described in your style instruction ({style}).\n" +
                "**Instructions:**\n" +
                "1.  **Analyze** the objective, category, and summary.\n" +
                "2.  **Use the CATEGORY** to determine the commit prefix/type. Use English prefixes: FEATURE -> feat, BUGFIX -> fix, REFACTOR -> refactor, STYLE -> style, DOCS -> docs, CHORE -> chore, PERFORMANCE -> perf, TEST -> test.\n" +
                "3.  **Use the OBJECTIVE** to write the concise title in the language specified by {language}.\n" +
                "4.  **Use the CHANGE SUMMARY** to write the body description.\n" +
                "5.  The title must be at most 72 characters.\n" +
                "6.  Separate the title from the body with a blank line.\n" +
                "7.  The title prefix (feat:, fix:, etc.) MUST be written in English. The title text MUST be in the language specified by {language}.\n" +
                "8.  The body MUST be written in the language specified by {language}.\n" +
                "9.  {language}\n" +
                "10. {style}\n" +
                "**CRITICAL:** Your response must contain ONLY the commit message. DO NOT include explanations, analysis, or any other text before or after the commit message.\n" +
                "**Output Validation:** Title must be max 72 characters with a blank line separating it from the body.\n" +
                "**Examples:**\n" +
                "- feat: Adicionar autenticação do usuário com OAuth2\n" +
                "\n" +
                "Implementa autenticação via OAuth2 com suporte a Google e GitHub. Adiciona fluxo de login, refresh token e gerenciamento de sessão.\n" +
                "- fix: Corrigir ponteiro nulo no carregamento do perfil\n" +
                "\n" +
                "Corrige NullPointerException ao carregar perfil de usuário sem foto. Adiciona verificação de nulidade antes de acessar campos da imagem."
    );



    companion object {
        fun fromValue(value: Int): LlmAgents? {
            return entries.find { it.value == value }
        }

        fun buildPrompt(baseInstructions: String, language: CommitLanguage, style: CommitStyle): String {
            return baseInstructions
                .replace("{language}", language.instruction)
                .replace("{style}", style.instruction)
        }
    }
}
