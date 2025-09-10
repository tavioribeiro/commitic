package org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components.registered_llm_list_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import commitic.composeapp.generated.resources.Res
import commitic.composeapp.generated.resources.icon_llm
import commitic.composeapp.generated.resources.icon_trash
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.core.extension.maskStart
import org.tavioribeiro.commitic.presentation.model.LlmUiModel
import org.tavioribeiro.commitic.theme.AppTheme


@Composable
fun RegisteredLlmListItem(
    llmUiModel: LlmUiModel,
    modifier: Modifier = Modifier,
    deleteLlm: (LlmUiModel) -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(120.dp)
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
                .clip(RoundedCornerShape(10.dp))
                .background(AppTheme.colors.color7),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                painter = painterResource(llmUiModel.iconResource!!),
                contentDescription = null,
                tint = AppTheme.colors.onColor7
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = llmUiModel.company,
                color = AppTheme.colors.onColor5,
                style = MaterialTheme.typography.bodyLarge,

            )

            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = llmUiModel.model,
                color = AppTheme.colors.color5,
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "Chave: ${llmUiModel.apiToken.maskStart(5)}",
                color = AppTheme.colors.color5,
                style = MaterialTheme.typography.bodySmall
            )
        }

        IconButton(
            onClick = {
                deleteLlm(llmUiModel)
            }
        ){
            Icon(
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp),
                painter = painterResource(Res.drawable.icon_trash),
                contentDescription = null,
                tint = AppTheme.colors.color5
            )
        }
    }
}