package org.tavioribeiro.commitic.data.datasource.local

import org.tavioribeiro.commitic.data.model.ProjectDTOModel

class ProjectLocalDataSource {
    fun saveProject(project: ProjectDTOModel) {
        println("--- CAMADA DE DADOS ---")
        println("✅ Projeto salvo (simulado): ${project.name} no caminho ${project.path}")
        println("----------------------")
    }
}
