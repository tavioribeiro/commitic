package org.tavioribeiro.commitic.data.datasource.remote

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.tavioribeiro.commitic.data.model.apis.ClaudeRequest
import org.tavioribeiro.commitic.data.model.apis.Content
import org.tavioribeiro.commitic.data.model.apis.GeminiRequest
import org.tavioribeiro.commitic.data.model.apis.HuggingFaceRequest
import org.tavioribeiro.commitic.data.model.apis.Message
import org.tavioribeiro.commitic.data.model.apis.OpenAiApiResponse
import org.tavioribeiro.commitic.data.model.apis.OpenAiRequest
import org.tavioribeiro.commitic.data.model.apis.Part
import org.tavioribeiro.commitic.data.model.apis.QwenInput
import org.tavioribeiro.commitic.data.model.apis.QwenRequest
import org.tavioribeiro.commitic.domain.model.llm.LlmAvailableApis
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel




class LlmRemoteDataSource(private val client: HttpClient)  {

     suspend fun generateCommit(prompt: String, llmDomainModel: LlmDomainModel): String {

         println(LlmAvailableApis.fromValue(llmDomainModel.company))
        val response: HttpResponse = when (LlmAvailableApis.fromValue(llmDomainModel.company)) {
            LlmAvailableApis.OPENAI -> postOpenAiCompatible("https://api.openai.com/v1/chat/completions", prompt, llmDomainModel)
            LlmAvailableApis.GROQ -> postOpenAiCompatible("https://api.groq.com/openai/v1/chat/completions", prompt, llmDomainModel)
            LlmAvailableApis.DEEPSEEK -> postOpenAiCompatible("https://api.deepseek.com/chat/completions", prompt, llmDomainModel)
            LlmAvailableApis.OPEN_ROUTER -> postOpenAiCompatible("https://openrouter.ai/api/v1/chat/completions", prompt, llmDomainModel)
            LlmAvailableApis.GEMINI -> callGeminiApi(prompt, llmDomainModel)
            LlmAvailableApis.QWEN -> callQwenApi(prompt, llmDomainModel)
            LlmAvailableApis.CLAUDE -> callClaudeApi(prompt, llmDomainModel)
            LlmAvailableApis.HUGGING_FACE -> callHuggingFaceApi(prompt, llmDomainModel)
            else -> throw IllegalArgumentException("API ${llmDomainModel.company} não suportada.")
        }


        if (!response.status.isSuccess()) {
            throw Exception("Erro na API [${response.status}]: ${response.bodyAsText()}")
        }

         val apiResponse = when (LlmAvailableApis.fromValue(llmDomainModel.company)) {
             LlmAvailableApis.OPENAI -> response.body<OpenAiApiResponse>()
             LlmAvailableApis.GROQ -> response.body<OpenAiApiResponse>()
             LlmAvailableApis.DEEPSEEK -> response.body<OpenAiApiResponse>()
             LlmAvailableApis.OPEN_ROUTER -> response.body<OpenAiApiResponse>()
             else -> response.body<OpenAiApiResponse>()
         }

         return apiResponse.choices.firstOrNull()?.message?.content?: throw Exception("A resposta da API não é válida.")
    }

    private suspend fun postOpenAiCompatible(url: String, prompt: String, config: LlmDomainModel): HttpResponse {
        val requestBody = OpenAiRequest(
            model = config.model,
            messages = listOf(Message(role = "user", content = prompt))
        )
        return client.post(url) {
            header(HttpHeaders.Authorization, "Bearer ${config.apiToken}")
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    private suspend fun callGeminiApi(prompt: String, config: LlmDomainModel): HttpResponse {
        val requestBody = GeminiRequest(contents = listOf(Content(parts = listOf(Part(text = prompt)))))
        return client.post("https://generativelanguage.googleapis.com/v1beta/models/${config.model}:generateContent") {
            url { parameters.append("key", config.apiToken) }
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    private suspend fun callQwenApi(prompt: String, config: LlmDomainModel): HttpResponse {
        val requestBody = QwenRequest(
            model = config.model,
            input = QwenInput(messages = listOf(Message(role = "user", content = prompt)))
        )
        return client.post("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation") {
            header(HttpHeaders.Authorization, "Bearer ${config.apiToken}")
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    private suspend fun callClaudeApi(prompt: String, config: LlmDomainModel): HttpResponse {
        val requestBody = ClaudeRequest(
            model = config.model,
            messages = listOf(Message(role = "user", content = prompt))
        )
        return client.post("https://api.anthropic.com/v1/messages") {
            header("x-api-key", config.apiToken)
            header("anthropic-version", "2023-06-01")
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    private suspend fun callHuggingFaceApi(prompt: String, config: LlmDomainModel): HttpResponse {
        val requestBody = HuggingFaceRequest(inputs = prompt)
        return client.post("https://api-inference.huggingface.co/models/${config.model}") {
            header(HttpHeaders.Authorization, "Bearer ${config.apiToken}")
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }
}