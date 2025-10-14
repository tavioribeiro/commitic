package org.tavioribeiro.commitic.presentation.features.main


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.tavioribeiro.commitic.theme.AppTheme
import org.tavioribeiro.commitic.theme.ThemeState
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import commitic.composeapp.generated.resources.Res
import commitic.composeapp.generated.resources.icon_clear
import commitic.composeapp.generated.resources.icon_commit
import commitic.composeapp.generated.resources.icon_folder
import commitic.composeapp.generated.resources.icon_history
import commitic.composeapp.generated.resources.icon_merge
import commitic.composeapp.generated.resources.icon_plus
import commitic.composeapp.generated.resources.icon_robot
import commitic.composeapp.generated.resources.icon_routine
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.presentation.components.buttons.IconTextButton
import org.tavioribeiro.commitic.presentation.components.buttons.UnderlineButton
import org.tavioribeiro.commitic.presentation.features.main.tabs.commits_tab.CommitsTab
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.LlmsTab
import org.tavioribeiro.commitic.presentation.features.main.tabs.projects_tab.ProjectsTab
import org.tavioribeiro.commitic.presentation.features.main.tabs.pull_request.PullRequestTab


@Composable
fun CalculatorScreen() {
    var selectedButton by remember { mutableStateOf("Commits") }

    Column(
        modifier = Modifier
            .background(AppTheme.colors.color1)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Row(
            Modifier.height(73.dp)
                .fillMaxWidth()
                .background(AppTheme.colors.color2)
                .padding(horizontal = 80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(Res.drawable.icon_clear),
                    contentDescription = "Meu Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(32.dp)
                )

                Text(
                    text = "Commitic",
                    color = AppTheme.colors.onColor1,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            IconTextButton(
                text = "Alterar Tema",
                onClick = {
                    ThemeState.toggleTheme()
                },
                icon = painterResource(Res.drawable.icon_routine),
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .width(1232.dp)
                .background(AppTheme.colors.color1)
                .padding(horizontal = 20.dp),
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                UnderlineButton(
                    text = "Commits",
                    icon = painterResource(Res.drawable.icon_commit),
                    isSelected = (selectedButton == "Commits"),
                    onClick = { selectedButton = "Commits" }
                )

                UnderlineButton(
                    text = "Pull Request",
                    icon = painterResource(Res.drawable.icon_merge),
                    isSelected = (selectedButton == "Pull Request"),
                    onClick = { selectedButton = "Pull Request" }
                )

                UnderlineButton(
                    text = "Projetos",
                    icon = painterResource(Res.drawable.icon_folder),
                    isSelected = (selectedButton == "Projetos"),
                    onClick = { selectedButton = "Projetos" }
                )

                UnderlineButton(
                    text = "Modelos",
                    icon = painterResource(Res.drawable.icon_robot),
                    isSelected = (selectedButton == "Modelos"),
                    onClick = { selectedButton = "Modelos" }
                )


                /*UnderlineButton(
                    text = "Histórico",
                    icon = painterResource(Res.drawable.icon_history),
                    isSelected = (selectedButton == "Histórico"),
                    onClick = { selectedButton = "Histórico" }
                )*/
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AppTheme.colors.color5)
            )

            Spacer(modifier = Modifier.height(32.dp))


            AnimatedContent(
                targetState = selectedButton,
                modifier = Modifier.fillMaxWidth(),
                transitionSpec = {
                    val exit = fadeOut(animationSpec = tween(300))

                    val enter = fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 300))

                    enter togetherWith exit
                },
                label = "TabContentAnimation"
            ){ targetState ->
                when (targetState) {
                    "Commits" -> CommitsTab()
                    "Pull Request" -> PullRequestTab()
                    "Projetos" -> ProjectsTab()
                    "Modelos" -> LlmsTab()
                }
            }
        }
    }
}
