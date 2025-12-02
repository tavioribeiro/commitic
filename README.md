# Commit 🤖✍️

**Commit** é uma ferramenta para automatizar e aprimorar seu fluxo de trabalho no Git. Utilizando o poder de modelos de linguagem (LLMs), ela transforma suas alterações de código em mensagens de commit e descrições de Pull Request claras, contextuais e profissionais.

Conecte seu modelo de IA preferido, aponte para seus repositórios e deixe que a automação cuide do trabalho repetitivo. Mantenha um histórico de versionamento limpo e significativo sem esforço.


<br>


<img width="1599" height="820" alt="Aba de adicionar ou remover projetos." src="https://github.com/user-attachments/assets/2ba2b0d7-1d5b-482e-9e44-ee5a2ef33a24" />
<p><em>Aba de adicionar ou remover projetos.</em></p>
<br>

<img width="1599" height="821" alt="Aba de adicionar ou remover modelos LLMs." src="https://github.com/user-attachments/assets/6c2992b7-7a33-4bad-8248-491247c489dd" />
<p><em>Aba de adicionar ou remover modelos LLMs.</em></p>
<br>

<img width="1599" height="825" alt="Pasta incorreta." src="https://github.com/user-attachments/assets/6c76d230-7072-4283-9592-59584c8ebec2" />
<p><em>Pasta incorreta.</em></p>
<br>

<img width="1599" height="824" alt="Aba com o texto do commit gerado pelo modelo automaticamente." src="https://github.com/user-attachments/assets/c1b161b7-089f-41b7-876a-beee0ba7f2e4" />
<p><em>Aba com o texto do commit gerado pelo modelo automaticamente.</em></p>
<br>

<img width="1599" height="825" alt="Aba com o texto de abertura de Pull Request gerado a partir do histórico de commits do projeto selecionado e da branch atual." src="https://github.com/user-attachments/assets/04ae4bd6-375f-4a77-9f38-9b6572415d3f" />
<p><em>Aba com o texto de abertura de Pull Request gerado a partir do histórico de commits do projeto selecionado e da branch atual.</em></p>
<br>

<img width="1599" height="824" alt="Aba de projetos usando o tema claro." src="https://github.com/user-attachments/assets/46108339-915b-48e1-813c-9da6cf04e074" />
<p><em>Aba de projetos usando o tema claro.</em></p>

<br>

---


## 🚀 Começando

A instalação é simples e direta.
Basta baixar o arquivo apropriado para seu sistema operacional na **[página de Releases](https://github.com/tavioribeiro/commitic/releases)** do projeto.

*   **Linux**: Baixe o pacote `.deb`
*   **Windows**: Baixe o instalador `.exe`


Você também pode instalar o pacote debian via terminal:

```console
# Adiciona o repo aos fontes
echo "deb [trusted=yes] https://apt.fury.io/tavioribeirodev/ /" | sudo tee /etc/apt/sources.list.d/commitic.list

sudo apt update
sudo apt install commitic
```

<br>

---

## 💻 Compatibilidade

| SO | Status |
| :--- | :---: |
| 🐧 Linux | ✅ |
| 🪟 Windows | ✅ |
| 🍏 Mac | ⏳ |


<br>

---


## 👨‍💻 Como Usar

1.  **📂 Cadastre seus Projetos**: No menu principal, adicione o caminho para os seus repositórios Git locais.
2.  **🤖 Configure seus Agentes de IA**: Acesse a área de "Agentes", escolha seu provedor de LLM preferido e adicione sua chave de API e o nome do modelo que deseja usar.
3.  **📝 Faça suas Alterações**: Trabalhe no seu código como de costume.
4.  **✍️ Gere o Commit**: Volte ao Commit, selecione os arquivos alterados e clique para que a IA analise as `diffs` e gere uma mensagem de commit inteligente.
5.  **🚀 Crie a Pull Request**: Após acumular vários commits, use a função de gerar PR para que a ferramenta crie uma descrição completa e bem estruturada para você.

## ✨ Funcionalidades Principais

*   **✍️ Geração de Commits com IA**: Analisa as `diffs` dos arquivos e sugere mensagens de commit inteligentes.
*   **🚀 Automação de Pull Requests**: Gera textos descritivos para suas PRs com base nas mudanças acumuladas.
*   **📂 Gerenciamento Centralizado de Projetos**: Adicione e gerencie múltiplos repositórios Git locais em um só lugar.
*   **🤖 Suporte a Múltiplos LLMs**: Conecte-se com os principais provedores de modelos de linguagem do mercado.

<br>

---

## 🤖 Agentes de IA Suportados

O Commit é compatível com uma variedade de provedores de LLM. Basta ter sua chave de API em mãos.

*   **OPENAI**
*   **GEMINI**
*   **OPEN_ROUTER**
*   **GROQ**
*   **DEEPSEEK**
*   **CLAUDE**
*   **QWEN**
*   **HUGGING_FACE**

---
