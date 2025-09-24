package org.tavioribeiro.commitic.data.datasource.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.tavioribeiro.commitic.data.model.dtos.CommitDTOModel
import org.tavioribeiro.commitic.db.CommitSchemaQueries


class CommitLocalDataSource(private val db: CommitSchemaQueries) {

    suspend fun saveCommit(commit: CommitDTOModel): String {
        try {
            withContext(Dispatchers.IO){
                delay(800)

                /*db.insertCommit(
                    name = commit.name,
                    directory_path = commit.path
                )*/
            }
            return "✅ Projeto salvo: ${commit.name} no caminho ${commit.path}"
        } catch (e: Exception) {
            throw e
        }
    }



    suspend fun getCommits(): List<CommitDTOModel> {
        return withContext(Dispatchers.IO){
            try {
                emptyList<CommitDTOModel>()

                /*val commitsFromDb = db.selectAllCommits().executeAsList()

                commitsFromDb.map { commitEntity ->
                    CommitDTOModel(
                        id = commitEntity.commit_id,
                        name = commitEntity.name,
                        path = commitEntity.directory_path
                    )
                }*/
            } catch (e: Exception) {
                throw e
            }
        }
    }


    suspend fun deleteCommit(commit: CommitDTOModel): String {
        try {
            withContext(Dispatchers.IO){
                delay(800)

                if(commit.id != null){
                    db.deleteCommitById(commit.id)
                }

            }
            return "✅ Projeto Deletado: ${commit.name} no caminho ${commit.path}"
        } catch (e: Exception) {
            throw e
        }
    }
}
