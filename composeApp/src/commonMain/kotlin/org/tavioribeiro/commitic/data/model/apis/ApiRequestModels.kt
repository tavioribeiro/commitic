package org.tavioribeiro.commitic.data.model.apis

import kotlinx.serialization.Serializable


@Serializable
data class OpenAiRequest(val model: String, val messages: List<Message>)
@Serializable
data class Message(val role: String, val content: String)


//------

@Serializable
data class GeminiRequest(val contents: List<Content>)
@Serializable
data class Content(val parts: List<Part>)
@Serializable
data class Part(val text: String)


//------

@Serializable
data class QwenRequest(val model: String, val input: QwenInput)
@Serializable
data class QwenInput(val messages: List<Message>)


//------

@Serializable
data class ClaudeRequest(val model: String, val messages: List<Message>, val max_tokens: Int = 1024)


//------

@Serializable
data class HuggingFaceRequest(val inputs: String) {
}