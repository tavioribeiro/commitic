package org.tavioribeiro.commitic.data.datasource.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tavioribeiro.commitic.data.model.ProjectDTOModel
import org.tavioribeiro.commitic.db.ProjectSchemaQueries


class ProjectLocalDataSource(private val db: ProjectSchemaQueries) {

    suspend fun saveProject(project: ProjectDTOModel): String {
        try {
            coroutineScope {
                launch(Dispatchers.IO) {
                    delay(800)

                    db.insertProject(
                        name = project.name,
                        directory_path = project.path
                    )
                }
            }


            return "✅ Projeto salvo: ${project.name} no caminho ${project.path}"
        } catch (e: Exception) {
            throw e
        }
    }



    suspend fun getProjects(): List<ProjectDTOModel> {
        try {
            return withContext(Dispatchers.IO) {
                val projectsFromDb = db.selectAllProjects().executeAsList()

                projectsFromDb.map { projectEntity ->
                    ProjectDTOModel(
                        id = projectEntity.project_id,
                        name = projectEntity.name,
                        path = projectEntity.directory_path
                    )
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }


    suspend fun deleteProject(project: ProjectDTOModel): String {
        try {
            coroutineScope {
                launch(Dispatchers.Main) {
                    delay(800)

                    if(project.id != null){
                        db.deleteBranchById(project.id)
                    }
                }
            }
            return "✅ Projeto Deletado: ${project.name} no caminho ${project.path}"
        } catch (e: Exception) {
            throw e
        }
    }
}
