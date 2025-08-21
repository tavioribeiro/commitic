package org.tavioribeiro.commitic.presentation.features.main.tabs.project_tab.components.registered_project_list_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import commitic.composeapp.generated.resources.Res
import commitic.composeapp.generated.resources.icon_code
import commitic.composeapp.generated.resources.icon_folder
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.theme.AppTheme



@Composable
fun RegisteredProjectListItem() {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(AppTheme.colors.color2)
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .background(AppTheme.colors.color7)
                .clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center

        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .height(40.dp)
                    .width(40.dp),
                painter = painterResource(Res.drawable.icon_code),
                contentDescription = null,
                tint = AppTheme.colors.onColor7
            )
        }
    }
}