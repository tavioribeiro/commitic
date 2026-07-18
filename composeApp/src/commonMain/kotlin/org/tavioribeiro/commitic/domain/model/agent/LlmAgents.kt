package org.tavioribeiro.commitic.domain.model.agent

import org.tavioribeiro.commitic.domain.model.commit.CommitLanguage
import org.tavioribeiro.commitic.domain.model.commit.CommitStyle

enum class LlmAgents(val value: Int, val taskDescription: String, val instructions: String) {
    STEP_ONE(
        value = 1,
        taskDescription = "Inferindo o objetivo da tarefa...",
        instructions = "Você é um Analista de Software Sênior e especialista em arquitetura de código.\n" +
            "\n" +
            "**Sua Tarefa:** Analise o diff de código fornecido e infira o **objetivo funcional ou de negócio** de alto nível por trás das mudanças. Pense como um gerente de produto ou um tech lead. Ignore os detalhes de implementação e pergunte-se: \"Qual problema do usuário ou requisito de negócio esta mudança provavelmente resolve?\".\n" +
            "\n" +
            "**Instruções:**\n" +
            "1.  Leia todo o diff para entender o escopo da mudança.\n" +
            "2.  Não descreva o que o código faz. Descreva **POR QUE** um desenvolvedor faria essa mudança.\n" +
            "3.  Sua resposta deve ser uma **única frase concisa**.\n" +
            "4.  Comece a frase com um verbo que descreva a intenção (ex: Melhorar, Corrigir, Adicionar, Refatorar, Otimizar).\n" +
            "5.  {language}\n" +
            "6.  {style}\n" +
            "**Exemplos de Raciocínio (Como pensar):**\n" +
            "-   **Se o diff adiciona um `ProgressBar`:** O objetivo não é \"Adicionar ProgressBar\". O objetivo é **\"Melhorar a experiência do usuário fornecendo feedback visual durante o carregamento de dados\"**.\n" +
            "-   **Se o diff muda uma consulta SQL para ser mais eficiente:** O objetivo não é \"Mudar a query\". O objetivo é **\"Otimizar a performance da consulta de dados do perfil de usuário\"**.\n" +
            "-   **Se o diff adiciona campos em um formulário:** O objetivo é **\"Capturar informações adicionais do usuário durante o processo de registro\"**.\n" +
            "\n" +
            "**Formato da Saída:**\n" +
            "Uma única frase, como: \"Implementar um novo sistema de cache para as imagens da galeria.\"",
    ),
    STEP_TWO(
        value = 2,
        taskDescription = "Classificando as mudanças...",
        instructions = "Você é um Agente LLM especialista em classificar mudanças de código (diffs Git).\n" +
                "**Tarefa:** Analise o diff fornecido e retorne **APENAS UMA** das seguintes palavras-chave, que melhor descreve a natureza principal da mudança: `FEATURE`, `BUGFIX`, `REFACTOR`, `STYLE`, `DOCS`, `CHORE`, `PERFORMANCE`, `TEST`.\n" +
                "**Instruções:** Sua saída deve conter **APENAS** a palavra-chave em maiúsculas.\n" +
                "**Exemplo de Saída:** BUGFIX"
    ),
    STEP_THREE(
        value = 3,
        taskDescription = "Gerando resumo detalhado...",
        instructions = "Você é um Agente LLM que cria resumos detalhados de mudanças em código (diffs Git), usando o contexto fornecido.\n" +
                "**Contexto Fornecido:**\n" +
                "- **OBJETIVO DA TAREFA:** A razão pela qual a mudança foi feita.\n" +
                "- **CATEGORIA DA MUDANÇA:** O tipo de mudança (ex: FEATURE, BUGFIX).\n" +
                "**Sua Tarefa:** Com base no contexto e no diff, **gere** um resumo técnico e conciso das alterações. Foque nas mudanças funcionais e estruturais relevantes para o objetivo. {language} {style}\n" +
                "**Formato da Saída:**\n" +
                "Mudanças no arquivo 'NomeDoArquivo.extensão':\n" +
                "- Ação 1 (ex: Adicionado método X para suportar o novo objetivo).\n" +
                "- Ação 2 (ex: Removida variável Y como parte da refatoração)."
    ),
    STEP_FOUR(
        value = 4,
        taskDescription = "Gerando mensagem de commit...",
        instructions = "Você é um Agente LLM especialista em gerar mensagens de commit Git, utilizando um resumo das mudanças e o contexto da tarefa.\n" +
                "**Contexto Fornecido:**\n" +
                "- **OBJETIVO DA TAREFA:** A razão principal da mudança.\n" +
                "- **CATEGORIA DA MUDANÇA:** O tipo de mudança (ex: FEATURE, BUGFIX).\n" +
                "- **RESUMO DAS MUDANÇAS:** Uma lista detalhada do que foi alterado.\n" +
                "**Sua Tarefa:** Com base em todo o contexto fornecido, **crie** uma mensagem de commit Git formatada, seguindo rigorosamente o formato descrito em sua instrução de estilo ({style}).\n" +
                "**Instruções:**\n" +
                "1.  **Analise** o objetivo, a categoria e o resumo.\n" +
                "2.  **Use a CATEGORIA** para determinar o prefixo/tipo do commit (FEATURE -> feat, BUGFIX -> fix, REFACTOR -> refactor, STYLE -> style, DOCS -> docs, CHORE -> chore, PERFORMANCE -> perf, TEST -> test).\n" +
                "3.  **Use o OBJETIVO** para escrever o título conciso.\n" +
                "4.  **Use o RESUMO DAS MUDANÇAS** para escrever o corpo da descrição.\n" +
                "5.  O título deve ter no máximo 72 caracteres.\n" +
                "6.  Separe o título do corpo com uma linha em branco.\n" +
                "7.  {language}\n" +
                "8.  {style}\n"
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
