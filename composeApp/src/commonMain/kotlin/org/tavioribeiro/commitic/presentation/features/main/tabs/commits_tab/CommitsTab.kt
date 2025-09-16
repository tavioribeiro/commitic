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
import commitic.composeapp.generated.resources.icon_folder
import commitic.composeapp.generated.resources.icon_plus
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.core.utils.DirectoryPicker
import org.tavioribeiro.commitic.presentation.components.buttons.IconTextButton
import org.tavioribeiro.commitic.presentation.components.inputs.FileInput
import org.tavioribeiro.commitic.presentation.components.inputs.FullInput
import org.tavioribeiro.commitic.theme.AppTheme
import org.tavioribeiro.commitic.core.utils.WindowType
import org.tavioribeiro.commitic.core.utils.getWindowSize
import org.tavioribeiro.commitic.presentation.features.main.tabs.commits_tab.components.registered_commit_list_item.RegisteredCommitListItem
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
import org.tavioribeiro.commitic.presentation.model.CommitUiModel

@Composable
fun CommitsTab(commitsTabviewModel: CommitsTabViewModel = koinInject()) {
    val windowSize = getWindowSize()
    val isMedium = windowSize.width == WindowType.Medium

    val commitsTabuiState by commitsTabviewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        commitsTabviewModel.loadCommits()
    }

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
            Column(
                Modifier
                    .height(425.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppTheme.colors.color3)
                    .border(1.dp, AppTheme.colors.color4, RoundedCornerShape(10.dp))
                    .padding(horizontal = 25.dp, vertical = 25.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(AppTheme.colors.color3)
                        .border(1.dp, AppTheme.colors.color4, RoundedCornerShape(10.dp))
                        .padding(horizontal = 25.dp, vertical = 25.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    SelectInput(
                        title = "Escolha o Projeto",
                        placeholder = "Seu projeto favorito",
                        options = commitsTabuiState.availableProjects,
                        initialPosition = commitsTabuiState.selectedProjectIndex,
                        onValueChange = { newCompany ->
                            //newLlmUiModel = newLlmUiModel.copy(company = newCompany ?: "")
                        },
                        isBackgroudColorDark = true
                    )
                }


                 Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                 ){
                     IconTextButton(
                         modifier = Modifier.padding(16.dp),
                         text = "Adicionar esse projeto",
                         onClick = {
                             //ThemeState.toggleTheme()
                             coroutineScope.launch(Dispatchers.Main) {
                                 commitsTabviewModel.onSaveCommitClicked(newCommitUiModel)
                             }
                         },
                         icon = painterResource(Res.drawable.icon_plus),
                         isLoading = commitsTabuiState.isLoading
                     )
                }

        }
    }
}