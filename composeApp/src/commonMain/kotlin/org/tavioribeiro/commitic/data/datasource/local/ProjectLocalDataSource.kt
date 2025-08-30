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
        println("✅ Projeto salvo: ${project.name} no caminho ${project.path}")
    }

    suspend fun getProjects(): List<ProjectDTOModel> {
        coroutineScope {
            launch(Dispatchers.Main) {
                delay(800)
            }
        }

        return listOf(
            ProjectDTOModel(id = 1, name = "Commitic", path = "/home/tavioribeiro/AndroidStudioProjects/Commitic"),
            ProjectDTOModel(id = 2, name = "Check Bus", path = "/home/tavioribeiro/Desktop/React/CheckBus"),
            ProjectDTOModel(id = 3, name = "LabZ", path = "/home/tavioribeiro/Desktop/ML/LabZ")
        )
    }


    fun deleteProject(project: ProjectDTOModel): String { //TODO("Trocar por um enum")
        try {
            println("✅ Projeto Deletado: ${project.name} no caminho ${project.path}")
            return ""
        } catch (e: Exception) {
            throw e
        }

    }
}
