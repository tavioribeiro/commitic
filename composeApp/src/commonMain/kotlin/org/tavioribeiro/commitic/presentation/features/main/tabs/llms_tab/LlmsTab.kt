package org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

@Composable
fun LlmsTab(
    viewModel: LLMsTabViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()
    var configIdToDelete by remember { mutableStateOf<Long?>(null) }

    if (configIdToDelete != null) {
        AlertDialog(
            onDismissRequest = { configIdToDelete = null },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Você tem certeza de que deseja deletar esta configuração?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        configIdToDelete?.let { viewModel.deleteConfig(it) }
                        configIdToDelete = null
                    }
                ) {
                    Text("Deletar")
                }
            },
            dismissButton = {
                TextButton(onClick = { configIdToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Adicionar Nova Configuração", style = MaterialTheme.typography.titleLarge)
        LlmsFormSection(
            uiState = uiState,
            viewModel = viewModel
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Configurações Salvas", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.isLoading && uiState.configs.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.configs.isEmpty()) {
            Text(
                text = "Nenhuma configuração salva ainda.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.configs, key = { it.id!! }) { config ->
                    ConfigListItem(
                        config = config,
                        onDeleteClick = { configIdToDelete = config.id }
                    )
                }
            }
        }
    }
}