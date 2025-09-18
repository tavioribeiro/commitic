package org.tavioribeiro.commitic.presentation.features.main.tabs.commits_tab

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import commitic.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.presentation.components.buttons.IconTextButton
import org.tavioribeiro.commitic.theme.AppTheme
import org.tavioribeiro.commitic.core.utils.WindowType
import org.tavioribeiro.commitic.core.utils.getWindowSize
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import commitic.composeapp.generated.resources.icon_commit
import commitic.composeapp.generated.resources.icon_folder
import commitic.composeapp.generated.resources.icon_no_projects
import commitic.composeapp.generated.resources.icon_plus
import kotlinx.coroutines.Dispatchers
import org.koin.compose.koinInject
import org.tavioribeiro.commitic.core.utils.DirectoryPicker
import org.tavioribeiro.commitic.presentation.components.inputs.FileInput
import org.tavioribeiro.commitic.presentation.components.inputs.FullInput
import org.tavioribeiro.commitic.presentation.components.select.SelectInput
import org.tavioribeiro.commitic.presentation.features.main.tabs.projects_tab.components.registered_project_list_item.RegisteredProjectListItem
import org.tavioribeiro.commitic.presentation.model.CommitUiModel

@Composable
fun CommitsTab(commitsTabviewModel: CommitsTabViewModel = koinInject()) {
    val windowSize = getWindowSize()
    val isMedium = windowSize.width == WindowType.Medium

    val commitsTabuiState by commitsTabviewModel.uiState.collectAsState()


    //LaunchedEffect(Unit) {}

    var newCommitUiModel by remember { mutableStateOf(
        CommitUiModel(
            id = 0,
            name = "",
            path = ""
        )
    )}


    val coroutineScope = rememberCoroutineScope()




    if (isMedium) {
        Column(
            modifier = Modifier
                .background(AppTheme.colors.color1)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /*Column(
                Modifier
                    .height(391.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppTheme.colors.onColor2)
                    .border(1.dp, AppTheme.colors.color4, RoundedCornerShape(10.dp))
                    .padding(horizontal = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Commitic",
                    color = AppTheme.colors.onColor1,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 8.dp)
                )

            }
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                Modifier
                    .height(391.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppTheme.colors.color3)
                    .border(1.dp, AppTheme.colors.color4, RoundedCornerShape(10.dp))
                    .padding(horizontal = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }*/
        }
    } else {
        Row(
            modifier = Modifier
                .background(AppTheme.colors.color1)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .height(530.dp)
                    .width(394.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppTheme.colors.color3)
                    .border(1.dp, AppTheme.colors.color4, RoundedCornerShape(10.dp))
                    .padding(horizontal = 25.dp, vertical = 25.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Gerar Commit",
                    color = AppTheme.colors.onColor5,
                    style = MaterialTheme.typography.headlineSmall
                )


                SelectInput(
                    title = "Escolha o Projeto",
                    placeholder = "Seu projeto favorito",
                    options = commitsTabuiState.availableProjectSelectOptions,
                    initialPosition = commitsTabuiState.selectedProjectIndex,
                    onValueChange = { selectedItemValue ->
                        commitsTabviewModel.onProjectSelected(selectedItemValue) },
                    modifier = Modifier.padding(top = 0.dp),
                    isBackgroudColorDark = true
                )
                Text(
                    text = commitsTabuiState.currentBranch,
                    color = AppTheme.colors.color7,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp, top = 10.dp)
                )




                SelectInput(
                    title = "Escolha o Modelo",
                    placeholder = "Seu modelo favorito",
                    options = commitsTabuiState.availableLlmSelectOptions,
                    initialPosition = commitsTabuiState.selectedLlmIndex,
                    onValueChange = { newCompany ->
                        //newLlmUiModel = newLlmUiModel.copy(company = newCompany ?: "")
                    },
                    modifier = Modifier.padding(top = 0.dp),
                    isBackgroudColorDark = true
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    IconTextButton(
                        modifier = Modifier.padding(top = 52.dp),
                        text = "Gerar Commit",
                        onClick = {
                            coroutineScope.launch(Dispatchers.Main) {
                                commitsTabviewModel.onSaveCommitClicked(newCommitUiModel)
                            }
                        },
                        icon = painterResource(Res.drawable.icon_commit),
                        isLoading = commitsTabuiState.isProjectLoading
                    )
                }
            }


            Spacer(modifier = Modifier.width(30.dp))


            Column(
                Modifier
                    .height(530.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppTheme.colors.color3)
                    .border(1.dp, AppTheme.colors.color4, RoundedCornerShape(10.dp))
                    .padding(horizontal = 25.dp, vertical = 25.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Projetos Registrados",
                    color = AppTheme.colors.onColor5,
                    style = MaterialTheme.typography.headlineSmall
                )

                val logText = """
INICIANDO PROCESSO DE GERAÇÃO DE COMMIT AUTOMATIZADO...

PASSO -1: Inferindo o objetivo da tarefa...
OBJETIVO INFERIDO: Melhorar a experiência do usuário ao tratar listas de seleção vazias, exibindo texto de placeholder e desabilitando a interação quando não há opções disponíveis.

PASSO 0: Classificando as mudanças...
CATEGORIA IDENTIFICADA: FEATURE

PASSO 1: Gerando resumo detalhado...

---------- RESUMO GERADO ----------
Mudanças no arquivo **SelectInput.kt**:
- Adicionado parâmetro `emptyStateText` com valor padrão “Não há opções disponíveis”.
- Introduzida variável `isEmpty` para identificar listas de opções vazias.
- Ajustada lógica de seleção: o rótulo só é calculado quando a lista não está vazia.
- Impedido que o dropdown seja expandido quando `isEmpty` for true.
- Definido `placeholderColor` e usado como cor de texto, borda e ícone desabilitado.
- Alterado o valor exibido do campo de texto para `emptyStateText` quando a lista está vazia.
- Desabilitada a interação do campo (`enabled = !isEmpty`) em estado vazio.
- Atualizadas as cores de placeholder e de estado desabilitado para refletir o tema escuro ou claro.
- Envolvido o `ExposedDropdownMenu` em verificação `if (!isEmpty)` para que o menu não seja renderizado quando não houver opções.

Mudanças no arquivo **CommitsTabViewModel.kt**:
- Pequena refatoração de formatação: ajuste de espaço ao atribuir o comando `git branch --show-current`. (sem impacto funcional).
-----------------------------------

PASSO 2: Gerando mensagem de commit...

---------- COMMIT GERADO ----------
✨ Melhorar experiência em selects vazios
📜 Descrição:
- Adicionado parâmetro `emptyStateText` com padrão “Não há opções disponíveis”.
- Criada variável `isEmpty` para detectar listas sem opções.
- Lógica de label ajustada para calcular somente quando a lista não está vazia.
- Dropdown impedido de expandir quando `isEmpty` é true.
- Definido `placeholderColor` aplicado a texto, borda e ícone desabilitado.
- Campo de texto exibe `emptyStateText` em estado vazio.
- Interação desabilitada (`enabled = !isEmpty`) quando não há opções.
- Cores de placeholder e estado desabilitado adaptadas ao tema claro/escuro.
- `ExposedDropdownMenu` renderizado apenas se `!isEmpty`.
- Refatoração menor em `CommitsTabViewModel.kt` (formatação do comando git).
-----------------------------------
    """.trimIndent()

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(AppTheme.colors.color2)
                        .padding(horizontal = 20.dp, vertical = 3.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    val scrollState = rememberScrollState()

                    BasicTextField(
                        value = logText,
                        onValueChange = {},
                        readOnly = true,
                        textStyle = TextStyle(color = AppTheme.colors.onColor1),
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    )
                }
            }
        }
    }
}