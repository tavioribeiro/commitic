package org.tavioribeiro.commitc.presentation.features.main.tabs

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
import org.tavioribeiro.commitc.presentation.components.buttons.ActionButton
import org.tavioribeiro.commitc.presentation.components.inputs.MidInput
import org.tavioribeiro.commitc.theme.AppTheme
import org.tavioribeiro.commitc.theme.ThemeState
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import commitc.composeapp.generated.resources.Res
import commitc.composeapp.generated.resources.icon_clear
import commitc.composeapp.generated.resources.icon_folder
import commitc.composeapp.generated.resources.icon_history
import commitc.composeapp.generated.resources.icon_plus
import commitc.composeapp.generated.resources.icon_robot
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitc.presentation.components.buttons.IconTextButton
import org.tavioribeiro.commitc.presentation.components.buttons.UnderlineButton


@Composable
fun AiAgentsTab() {
    Column(
        Modifier.height(391.dp)
            .fillMaxWidth()
            .background(AppTheme.colors.color2)
            .padding(horizontal = 25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}