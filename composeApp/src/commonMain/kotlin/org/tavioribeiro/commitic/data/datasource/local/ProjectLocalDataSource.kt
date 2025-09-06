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
            withContext(Dispatchers.IO){
                delay(8000)

                db.insertProject(
                    name = project.name,
                    directory_path = project.path
                )
            }
            return "✅ Projeto salvo: ${project.name} no caminho ${project.path}"
        } catch (e: Exception) {
            throw e
        }
    }



    suspend fun getProjects(): List<ProjectDTOModel> {
        return withContext(Dispatchers.IO){
            try {
                val projectsFromDb = db.selectAllProjects().executeAsList()

                projectsFromDb.map { projectEntity ->
                    ProjectDTOModel(
                        id = projectEntity.project_id,
                        name = projectEntity.name,
                        path = projectEntity.directory_path
                    )
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }


    suspend fun deleteProject(project: ProjectDTOModel): String {
        try {
            withContext(Dispatchers.IO){
                delay(800)

                if(project.id != null){
                    db.deleteProjectById(project.id)
                }

            }
            return "✅ Projeto Deletado: ${project.name} no caminho ${project.path}"
        } catch (e: Exception) {
            throw e
        }
    }
}
