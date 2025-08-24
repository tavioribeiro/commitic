package org.tavioribeiro.commitic.data.datasource.local

import org.tavioribeiro.commitic.data.model.ProjectDTOModel

class ProjectLocalDataSource {
    fun saveProject(project: ProjectDTOModel) {
        println("--- CAMADA DE DADOS ---")
        println("✅ Projeto salvo (simulado): ${project.name} no caminho ${project.path}")
        println("----------------------")
    }

    fun getProjects(): List<ProjectDTOModel> {
        return listOf(
            ProjectDTOModel(id = 1, name = "Commitic", path = "/home/tavioribeiro/AndroidStudioProjects/Commitic"),
            ProjectDTOModel(id = 2, name = "Check Bus", path = "/home/tavioribeiro/Desktop/React/CheckBus"),
            ProjectDTOModel(id = 3, name = "Projeto Secreto", path = "/path/to/secret")
        )
    }
}
