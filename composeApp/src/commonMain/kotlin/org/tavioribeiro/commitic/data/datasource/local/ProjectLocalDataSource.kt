package org.tavioribeiro.commitic.data.datasource.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tavioribeiro.commitic.data.model.ProjectDTOModel
import org.tavioribeiro.commitic.db.ProjectSchemaQueries


class ProjectLocalDataSource(private val db: ProjectSchemaQueries) {

    suspend fun saveProject(project: ProjectDTOModel): String {
        try {
            coroutineScope {
                launch(Dispatchers.Main) {
                    delay(800)
                }
            }


            db.insertProject(project.name)

            return "✅ Projeto salvo: ${project.name} no caminho ${project.path}"
        } catch (e: Exception) {
            throw e
        }
    }



    suspend fun getProjects(): List<ProjectDTOModel> {
        try {
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
        } catch (e: Exception) {
            throw e
        }
    }


    suspend fun deleteProject(project: ProjectDTOModel): String {
        try {
            coroutineScope {
                launch(Dispatchers.Main) {
                    delay(800)
                }
            }
            println("✅ Projeto Deletado: ${project.name} no caminho ${project.path}")
            return "✅ Projeto Deletado: ${project.name} no caminho ${project.path}"
        } catch (e: Exception) {
            throw e
        }
    }
}
