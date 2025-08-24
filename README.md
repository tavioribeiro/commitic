# Commit 🤖✍️

**Commit-AI** é uma ferramenta inteligente projetada para otimizar seu fluxo de trabalho no Git. Utilizando o poder de agentes LLM, ele analisa suas mudanças de código e gera automaticamente mensagens de commit e descrições de Pull Request claras, contextuais e significativas.

Diga adeus ao bloqueio de escrita na hora de commitar e mantenha um histórico de versionamento limpo e profissional sem esforço.


<img width="1599" height="860" alt="image" src="https://github.com/user-attachments/assets/2806e581-d16e-44ec-a861-70e4cf56a1e5" />

## ✨ Funcionalidades Principais

*   **✍️ Geração de Commits com IA**: Analisa os `diffs` dos arquivos e sugere mensagens de commit inteligentes.
*   **📂 Cadastro de Projetos**: Adicione e gerencie múltiplos repositórios Git locais em um só lugar.
*   **👀 Visualização de Mudanças**: Visualize facilmente os arquivos alterados e suas modificações antes de commitar.
*   **📖 Histórico de Commits**: Navegue pelo histórico de commits do seu projeto diretamente na interface.
*   **🚀 Automação de Pull Requests**: Gere textos descritivos para suas aberturas de PR com base nas mudanças acumuladas.
*   **🤖 Gerenciamento de Agentes**: Liste e configure diferentes modelos de LLM para a geração de texto.

---

## 🛠️ Tech Stack & Arquitetura

Este projeto foi construído com foco em escalabilidade, manutenibilidade e um código limpo, utilizando tecnologias e padrões modernos.

*   **Plataforma**: **Kotlin Multiplatform (KMP)**, permitindo que o código rode em:
    *   **Desktop (JVM)**
    *   **Web (WasmJs)**
*   **Interface de Usuário**: **Jetpack Compose para Multiplataforma**, para uma UI declarativa e reativa compartilhada.
*   **Arquitetura**: **Clean Architecture** combinada com o padrão **MVVM** na camada de apresentação.
    *   **`Domain`**
    *   **`Data`**
    *   **`Presentation`**
*   **Injeção de Dependência**: **Koin**, para gerenciar e fornecer as dependências de forma centralizada e desacoplada.
