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

                db.insertCommit(
                    project_id = commit.projectId,
                    branch_name = commit.branchName,
                    task_objective = commit.taskObjective,
                    category = commit.category,
                    summary = commit.summary,
                    commit_message = commit.commitMessage
                )
            }
            return ""
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
                   // db.deleteCommitById(commit.id)
                }

            }
            return "✅ "
        } catch (e: Exception) {
            throw e
        }
    }
}
