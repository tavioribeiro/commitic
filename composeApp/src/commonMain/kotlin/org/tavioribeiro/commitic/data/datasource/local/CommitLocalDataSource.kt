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



    suspend fun getCommitsByProjectIdAndBranch(projectId: Long, branchName: String): List<CommitDTOModel> {
        return withContext(Dispatchers.IO){
            try {
                val commitsFromDb = db.selectAllByProjectIdAndBranchName(projectId, branchName).executeAsList()

                commitsFromDb.map { commitEntity ->
                    CommitDTOModel(
                        id = commitEntity.id,
                        projectId = commitEntity.project_id,
                        branchName = commitEntity.branch_name,
                        taskObjective = commitEntity.task_objective,
                        category = commitEntity.category,
                        summary = commitEntity.summary,
                        commitMessage = commitEntity.commit_message
                    )
                }
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
