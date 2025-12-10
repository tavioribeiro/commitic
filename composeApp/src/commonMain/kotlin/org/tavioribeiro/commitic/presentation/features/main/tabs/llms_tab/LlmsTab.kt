package org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab

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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import commitic.composeapp.generated.resources.icon_plus
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.presentation.components.buttons.IconTextButton
import org.tavioribeiro.commitic.presentation.components.inputs.FullInput
import org.tavioribeiro.commitic.theme.AppTheme
import org.tavioribeiro.commitic.core.utils.WindowType
import org.tavioribeiro.commitic.core.utils.getWindowSize
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components.registered_llm_list_item.RegisteredLlmListItem
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import commitic.composeapp.generated.resources.icon_no_projects
import kotlinx.coroutines.Dispatchers
import org.koin.compose.koinInject
import org.tavioribeiro.commitic.presentation.components.select.SelectInput
import org.tavioribeiro.commitic.presentation.model.LlmUiModel

@Composable
fun LlmsTab(llmsTabviewModel: LlmsTabViewModel = koinInject()) {
    val windowSize = getWindowSize()
    val isMedium = windowSize.width == WindowType.Medium

    val llmsTabuiState by llmsTabviewModel.uiState.collectAsState()
    val llmNameInputWarningState by llmsTabviewModel.llmNameInputWarningState.collectAsState()
    val pathInputWarningState by llmsTabviewModel.pathInputWarningState.collectAsState()


    LaunchedEffect(Unit) {
        llmsTabviewModel.loadLlms()
    }

    var newLlmUiModel by remember { mutableStateOf(
        LlmUiModel(
            id = 0,
            model = "",
            company = "",
            apiToken = ""
        )
    )}

    val listState = rememberLazyListState()
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

            Column(
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

            }
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
                    text = "Adicionar Modelo LLM",
                    color = AppTheme.colors.onColor5,
                    style = MaterialTheme.typography.headlineSmall
                )

                SelectInput(
                    title = "API do Modelo",
                    placeholder = "Selecione uma API",
                    options = llmsTabuiState.apiOptions,
                    initialValue = newLlmUiModel.company,
                    onValueChange = { newCompany ->
                        newLlmUiModel = newLlmUiModel.copy(company = newCompany ?: "")
                    },
                    isBackgroudColorDark = true
                )

                FullInput(
                    modifier = Modifier.padding(top = 4.dp),
                    title = "ID do Modelo",
                    placeholder = "deepseek-chat-v3.1",
                    warning = llmNameInputWarningState,
                    initialValue = newLlmUiModel.model,
                    onValueChange = { newModel ->
                        newLlmUiModel = newLlmUiModel.copy(model = newModel)
                    },
                    isBackgroudColorDark = true
                )

                FullInput(
                    modifier = Modifier.padding(top = 4.dp),
                    title = "Chave da API",
                    placeholder = "f07c1367716290d39235cf642580ad2c",
                    warning = pathInputWarningState,
                    initialValue = newLlmUiModel.apiToken,
                    onValueChange = { newApiToken ->
                        newLlmUiModel = newLlmUiModel.copy(apiToken = newApiToken)
                    },
                    isBackgroudColorDark = true
                )

                Spacer(modifier = Modifier.weight(1f))


                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){

                    IconTextButton(
                        modifier = Modifier.padding(0.dp),
                        text = "Adicionar esse Modelo",
                        onClick = {
                            coroutineScope.launch(Dispatchers.Main) {
                                llmsTabviewModel.onSaveLlmClicked(newLlmUiModel)
                            }
                        },
                        icon = painterResource(Res.drawable.icon_plus),
                        isLoading = llmsTabuiState.isLoading
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
                    text = "Modelos LLMs Registrados",
                    color = AppTheme.colors.onColor5,
                    style = MaterialTheme.typography.headlineSmall
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    AnimatedContent(
                        targetState = llmsTabuiState.isLoading,
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
                        if (targetState) {
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
                        } else {
                            if(llmsTabuiState.llms.isEmpty()){
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                            .height(24.dp)
                                            .width(24 .dp),
                                        painter = painterResource(Res.drawable.icon_no_projects),
                                        contentDescription = null,
                                        tint = AppTheme.colors.onColor5
                                    )

                                    Text(
                                        text = "Sem modelo cadastrado",
                                        color = AppTheme.colors.onColor5,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                            else {
                                LazyColumn(
                                    state = listState,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .pointerInput(Unit) {
                                            detectDragGestures { change, dragAmount ->
                                                change.consume()
                                                coroutineScope.launch {
                                                    listState.scrollBy(-dragAmount.y)
                                                }
                                            }
                                        },
                                    contentPadding = PaddingValues(top = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(llmsTabuiState.llms.size) { index ->
                                        RegisteredLlmListItem(
                                            llmUiModel = llmsTabuiState.llms[index],
                                            deleteLlm = { llm ->
                                                coroutineScope.launch(Dispatchers.Main) {
                                                    llmsTabviewModel.deleteLlm(llm)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}