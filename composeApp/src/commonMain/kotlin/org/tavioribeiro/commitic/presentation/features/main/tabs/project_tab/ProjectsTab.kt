package org.tavioribeiro.commitic.presentation.features.main.tabs

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
import androidx.compose.foundation.verticalScroll
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
import org.tavioribeiro.commitic.general_utils.DirectoryPicker
import org.tavioribeiro.commitic.presentation.components.buttons.IconTextButton
import org.tavioribeiro.commitic.presentation.components.inputs.FileInput
import org.tavioribeiro.commitic.presentation.components.inputs.FullInput
import org.tavioribeiro.commitic.theme.AppTheme
import org.tavioribeiro.commitic.screen_utils.WindowType
import org.tavioribeiro.commitic.screen_utils.getWindowSize
import org.tavioribeiro.commitic.theme.ThemeState
import org.tavioribeiro.commitic.presentation.features.main.tabs.project_tab.components.registered_project_list_item.RegisteredProjectListItem


@Composable
fun ProjectsTab() {
    val windowSize = getWindowSize()
    val isMedium = windowSize.width == WindowType.Medium


    var projectName by remember { mutableStateOf("") }
    var filePath by remember { mutableStateOf("") }
    var showDirPicker by remember { mutableStateOf(false) }

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
                    .height(425.dp)
                    .width(394.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppTheme.colors.color3)
                    .border(1.dp, AppTheme.colors.color4, RoundedCornerShape(10.dp))
                    .padding(horizontal = 25.dp, vertical = 25.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Adicionar Novo Projeto",
                    color = AppTheme.colors.onColor5,
                    style = MaterialTheme.typography.headlineSmall
                )

                FullInput(
                    modifier = Modifier.padding(top = 20.dp),
                    title = "Nome do Projeto",
                    placeholder = "Meu Projeto Maravilhoso",
                    initialValue = "",
                    onValueChange = { newName ->
                        projectName = newName
                    },
                    isBackgroudColorDark = true
                )

                FileInput(
                    title = "Git Repository Path",
                    placeholder = "/endereco/do/repositorio",
                    icon = painterResource(Res.drawable.icon_folder),
                    initialValue = filePath,
                    onValueChange = { newPath ->
                        filePath = newPath
                    },
                    onFileSelect = {
                        println("Botão de selecionar arquivo clicado!")
                        showDirPicker = true
                    },
                    isBackgroudColorDark = true
                )

                DirectoryPicker(
                    show = showDirPicker,
                    title = "Selecione a pasta do repositório",
                    onResult = { path ->
                        showDirPicker = false
                        path?.let {
                            filePath = it
                        }
                    }
                )


                 Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                 ){
                     IconTextButton(
                         text = "Adicionar esse projeto",
                         onClick = {
                             ThemeState.toggleTheme()
                         },
                         icon = painterResource(Res.drawable.icon_plus),
                         modifier = Modifier.padding(16.dp)
                     )
                }


            }
            Spacer(modifier = Modifier.width(30.dp))

            Column(
                Modifier
                    .height(425.dp)
                    .height(391.dp)
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

                RegisteredProjectListItem()
            }
        }
    }
}