package org.tavioribeiro.commitic.data.datasource.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.tavioribeiro.commitic.data.model.dtos.LlmDTOModel
import org.tavioribeiro.commitic.db.LlmSchemaQueries


class LlmLocalDataSource(private val db: LlmSchemaQueries) {

    suspend fun saveLlm(llm: LlmDTOModel): String {
        try {
            withContext(Dispatchers.IO){
                delay(800)

                db.insertLlm(
                    company = llm.company,
                    model = llm.model,
                    api_token = llm.api_token
                )
            }
            return "✅ Modelo salvo: ${llm.company} - ${llm.model}"
        } catch (e: Exception) {
            throw e
        }
    }



    suspend fun getLlms(): List<LlmDTOModel> {
        return withContext(Dispatchers.IO){
            try {
                val llmsFromDb = db.getAllLlm().executeAsList()

                llmsFromDb.map { llmEntity ->
                    LlmDTOModel(
                        id = llmEntity.id,
                        company = llmEntity.company,
                        model = llmEntity.model,
                        api_token = llmEntity.api_token
                    )
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }


    suspend fun deleteLlm(llm: LlmDTOModel): String {
        try {
            withContext(Dispatchers.IO){
                delay(800)

                if(llm.id != null){
                    db.deleteLlmById(llm.id)
                }

            }
            return "✅ Modelo deletado: ${llm.company} - ${llm.model}"
        } catch (e: Exception) {
            throw e
        }
    }
}
