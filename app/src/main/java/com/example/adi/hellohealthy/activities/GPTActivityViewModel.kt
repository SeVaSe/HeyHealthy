package com.example.adi.hellohealthy.activities

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Ключ для доступа к API OpenAI GPT
const val CHAT_GPT_API_KEY = "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"

// ViewModel для активности GPTActivity
class GPTActivityViewModel : ViewModel() {
    // Запрос пользователя к GPT модели
    var userGptQuery = "Чем ты можешь помочь мне в спорте ?"
    // Ответ от GPT модели
    var gptResponse = ""
    // LiveData для передачи ответа от GPT модели в UI
    val gptResponseLiveData = MutableLiveData("")
    // Область корутины для выполнения асинхронных задач
    private val scope = CoroutineScope(Dispatchers.Main)

    // Метод для обновления запроса к GPT модели
    fun updateGptQueryQuestion(query: String) {
        userGptQuery = query
    }

    // Метод для выполнения запроса к GPT модели
    @OptIn(BetaOpenAI::class)
    fun makeGptCall() = scope.launch {
        val openAI = OpenAI(CHAT_GPT_API_KEY)
        try {
            Log.i("GPTAPP", "Starting request: $userGptQuery")
            // Формирование запроса к GPT модели
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"), messages = listOf(
                    ChatMessage(
                        role = ChatRole.User, content = userGptQuery
                    )
                )
            )
            // Выполнение запроса
            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)

            // Получение ответа от GPT модели
            val response = completion.choices.first().message?.content

            // Обновление ответа в LiveData для UI
            gptResponse = response ?: "..."
            gptResponseLiveData.value = gptResponseLiveData.value + "\n \n\n\n" + gptResponse
            Log.i("GPTAPP", "response: $gptResponse")
        } catch (e: Exception) {
            // В случае ошибки выводится соответствующее сообщение
            gptResponse = "Извини, не могу ответить("
            Log.i("GPTAPP", "fail with: $e")
        }
    }
}
