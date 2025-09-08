package org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components.registered_llm_list_item

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import commitic.composeapp.generated.resources.Res
import commitic.composeapp.generated.resources.icon_clear
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.domain.model.llm.LlmConfig

@Composable
fun ConfigListItem(
    config: LlmConfig,
    onDeleteClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(config.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = config.providerType.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(Res.drawable.icon_clear),
                    contentDescription = "Deletar Configuração",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}