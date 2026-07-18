package org.tavioribeiro.commitic.presentation.features.main.tabs.commits_tab

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import commitic.composeapp.generated.resources.icon_commit
import commitic.composeapp.generated.resources.icon_copy
import commitic.composeapp.generated.resources.icon_save
import commitic.composeapp.generated.resources.icon_voice_style
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.compose.koinInject
import org.tavioribeiro.commitic.domain.model.commit.CommitLanguage
import org.tavioribeiro.commitic.domain.model.commit.CommitStyle
import org.tavioribeiro.commitic.presentation.components.inputs.FullInput
import org.tavioribeiro.commitic.presentation.components.multistep.FiveStepStatus
import org.tavioribeiro.commitic.presentation.components.select.SelectInput
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastType
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel
import org.tavioribeiro.commitic.presentation.model.SelectOptionModel

@Composable
fun CommitsTab(
    commitsTabviewModel: CommitsTabViewModel = koinInject(),
    toastViewModel: ToastViewModel = koinInject(),
) {
    val windowSize = getWindowSize()
    val isMedium = windowSize.width == WindowType.Medium

    val commitsTabuiState by commitsTabviewModel.uiState.collectAsState()


    var delayBetweenStepsInputWarningState by remember { mutableStateOf("") }
    //LaunchedEffect(Unit) {}

    val clipboardManager = LocalClipboardManager.current






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

                println(commitsTabuiState.selectedProjectIndex)

                SelectInput(
                    title = "Escolha o Projeto",
                    placeholder = "Seu projeto favorito",
                    options = commitsTabuiState.availableProjectSelectOptions,
                    initialPosition = commitsTabuiState.selectedProjectIndex,
                    onValueChange = { selectedItemValue ->
                        commitsTabviewModel.onProjectSelected(selectedItemValue)
                    },
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
                    onValueChange = { newModelId ->
                        commitsTabviewModel.onLlmSelected(newModelId)
                    },
                    modifier = Modifier.padding(top = 0.dp),
                    isBackgroudColorDark = true
                )


                FullInput(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    title = "Delay entre passos",
                    suffixText = "segundos",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = commitsTabuiState.delayBetweenSteps.toString(),
                    warning = delayBetweenStepsInputWarningState,
                    initialValue = commitsTabuiState.delayBetweenSteps.toString(),
                    onValueChange = { seconds ->
                        if (seconds.isBlank()) {
                            delayBetweenStepsInputWarningState = ""
                            commitsTabviewModel.onChangeDelayBetweenSteps(0)

                        }

                        val number = seconds.toIntOrNull()

                        if (number != null) {
                            delayBetweenStepsInputWarningState = ""
                            commitsTabviewModel.onChangeDelayBetweenSteps(number)
                        } else {
                            delayBetweenStepsInputWarningState = "O valor deve ser um número inteiro."
                        }
                    },
                    isBackgroudColorDark = true
                )
                Text(
                    text = "Algumas API's, possuem um intervalo de segundos entre as chamadas.",
                    color = AppTheme.colors.onColor5,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )




                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { commitsTabviewModel.onToggleOptionsPopup() },
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AppTheme.colors.color5)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_voice_style),
                            contentDescription = "Opções de Commit",
                            tint = AppTheme.colors.onColor5,
                            modifier = Modifier.height(24.dp).width(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    IconTextButton(
                        text = "Gerar Commit",
                        enabled = delayBetweenStepsInputWarningState.isEmpty(),
                        onClick = {
                            coroutineScope.launch(Dispatchers.Main) {
                                if(commitsTabuiState.selectedProjectIndex != null && commitsTabuiState.selectedLlmIndex != null){
                                    commitsTabviewModel.onGenerateCommitClicked(commitsTabuiState.selectedProjectIndex!!, commitsTabuiState.selectedLlmIndex!!)
                                }
                                else {
                                    toastViewModel.showToast(
                                        ToastUiModel(
                                            title = "Atenção",
                                            message = "Selecione um projeto e um modelo de LLM.",
                                            type = ToastType.WARNING
                                        )
                                    )
                                }
                            }
                        },
                        icon = painterResource(Res.drawable.icon_commit),
                        isLoading = commitsTabuiState.isGenaratingCommitLoading
                    )
                }

                if (commitsTabuiState.isOptionsPopupVisible) {
                    CommitOptionsPopup(
                        selectedLanguage = commitsTabuiState.selectedLanguage,
                        selectedStyle = commitsTabuiState.selectedStyle,
                        onLanguageSelected = { commitsTabviewModel.onLanguageSelected(it) },
                        onStyleSelected = { commitsTabviewModel.onStyleSelected(it) },
                        onDismiss = { commitsTabviewModel.onToggleOptionsPopup() }
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
                    text = "Progresso da geração",
                    color = AppTheme.colors.onColor5,
                    style = MaterialTheme.typography.headlineSmall
                )

                FiveStepStatus(
                    fiveStepStatusModel = commitsTabuiState.stepsAndProgress,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(AppTheme.colors.color2)
                        .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    val scrollState = rememberScrollState()

                    BasicTextField(
                        value = commitsTabuiState.commitText,
                        onValueChange = {},
                        readOnly = true,
                        textStyle = TextStyle(color = AppTheme.colors.onColor1),
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    )

                    IconButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        onClick = {
                            clipboardManager.setText(AnnotatedString(commitsTabuiState.commitText))

                            toastViewModel.showToast(
                                ToastUiModel(
                                    title = "Sucesso",
                                    message = "Copiado!",
                                    type = ToastType.SUCCESS,
                                    duration = 2000L
                                )
                            )
                        }
                    ){
                        Icon(
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp),
                            painter = painterResource(Res.drawable.icon_copy),
                            contentDescription = null,
                            tint = AppTheme.colors.color5
                        )
                    }

                    AnimatedContent(
                        targetState = commitsTabuiState.isGenaratingCommitLoading,
                        modifier = Modifier.fillMaxSize(),
                        transitionSpec = {
                            val exit = fadeOut(animationSpec = tween(300))

                            val enter = fadeIn(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    delayMillis = 300
                                )
                            )

                            enter togetherWith exit
                        },
                        label = "TabContentAnimation"
                    ) { targetState ->
                        if(targetState){
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(30.dp),
                                    color = AppTheme.colors.onColor5
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AnimatedContent(
                        targetState = commitsTabuiState.isCommitGenerated,
                        transitionSpec = {
                            val exit = fadeOut(animationSpec = tween(300))

                            val enter = fadeIn(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    delayMillis = 300
                                )
                            )

                            enter togetherWith exit
                        },
                        label = "TabContentAnimation"
                    ) { targetState ->
                        if(targetState){
                            IconTextButton(
                                text = "Salvar Commit Gerado",
                                onClick = {
                                    coroutineScope.launch(Dispatchers.Main) {
                                        commitsTabviewModel.onSaveCommitClicked()
                                    }
                                },
                                icon = painterResource(Res.drawable.icon_save),
                                isLoading = commitsTabuiState.isSavingCommitLoading
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CommitOptionsPopup(
    selectedLanguage: CommitLanguage,
    selectedStyle: CommitStyle,
    onLanguageSelected: (CommitLanguage) -> Unit,
    onStyleSelected: (CommitStyle) -> Unit,
    onDismiss: () -> Unit
) {
    val languageOptions = CommitLanguage.entries.map {
        SelectOptionModel(label = it.displayName, value = it.name)
    }
    val styleOptions = CommitStyle.entries.map {
        SelectOptionModel(label = it.displayName, value = it.name)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Opções de Commit",
                color = AppTheme.colors.onColor2,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                SelectInput(
                    title = "Idioma",
                    placeholder = "Selecione o idioma",
                    options = languageOptions,
                    initialValue = selectedLanguage.name,
                    onValueChange = { value ->
                        val language = try { CommitLanguage.valueOf(value) } catch (e: IllegalArgumentException) { null }
                        if (language != null) onLanguageSelected(language)
                    },
                    isBackgroudColorDark = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                SelectInput(
                    title = "Estilo",
                    placeholder = "Selecione o estilo",
                    options = styleOptions,
                    initialValue = selectedStyle.name,
                    onValueChange = { value ->
                        val style = try { CommitStyle.valueOf(value) } catch (e: IllegalArgumentException) { null }
                        if (style != null) onStyleSelected(style)
                    },
                    isBackgroudColorDark = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar", color = AppTheme.colors.color7)
            }
        },
        containerColor = AppTheme.colors.color3,
        titleContentColor = AppTheme.colors.onColor2,
        textContentColor = AppTheme.colors.onColor2
    )
}