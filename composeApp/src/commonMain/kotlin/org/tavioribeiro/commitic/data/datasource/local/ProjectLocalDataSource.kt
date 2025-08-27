package org.tavioribeiro.commitic.data.datasource.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tavioribeiro.commitic.data.model.ProjectDTOModel
import kotlin.coroutines.coroutineContext

class ProjectLocalDataSource {
    fun saveProject(project: ProjectDTOModel) {
        println("--- CAMADA DE DADOS ---")
        println("✅ Projeto salvo (simulado): ${project.name} no caminho ${project.path}")
        println("----------------------")
    }

    /*suspend fun getProjects(): List<ProjectDTOModel> {
        return coroutineScope {
            launch(Dispatchers.Main) {
                delay(5000) // 5 seconds delay
            }

            listOf(
                ProjectDTOModel(
                    id = 1,
                    name = "Commitic",
                    path = "/home/tavioribeiro/AndroidStudioProjects/Commitic"
                ),
                ProjectDTOModel(
                    id = 2,
                    name = "Check Bus",
                    path = "/home/tavioribeiro/Desktop/React/CheckBus"
                ),
                ProjectDTOModel(id = 3, name = "Projeto Secreto", path = "/path/to/secret")
            )
        }
    }*/
    suspend fun getProjects(): List<ProjectDTOModel> {
        coroutineScope {
            launch(Dispatchers.Main) {
                delay(1000)
            }
        }

        return listOf(
            ProjectDTOModel(id = 1, name = "Commitic", path = "/home/tavioribeiro/AndroidStudioProjects/Commitic"),
            ProjectDTOModel(id = 2, name = "Check Bus", path = "/home/tavioribeiro/Desktop/React/CheckBus"),
            ProjectDTOModel(id = 3, name = "Projeto Secreto", path = "/path/to/secret")
        )
    }


    fun deleteProject(project: ProjectDTOModel) {
        println("✅ Projeto Deletado (simulado): ${project.name} no caminho ${project.path}")
    }
}
