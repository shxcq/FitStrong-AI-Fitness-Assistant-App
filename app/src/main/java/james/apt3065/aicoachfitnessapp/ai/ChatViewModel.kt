package james.apt3065.aicoachfitnessapp.ai

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    val messageList by lazy {
        mutableStateListOf<MessageModel>().apply {
            add(MessageModel("Hi, can I help you? Please ask a fitness-related question.", "model"))
        }
    }

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = Constants.apiKey
    )

    // Function to check if a question is related to fitness
    private fun isFitnessQuestion(question: String): Boolean {
        val fitnessKeywords = listOf("exercise", "workout", "fitness", "diet", "nutrition", "training", "health","hey","hello","thanks")
        return fitnessKeywords.any { question.contains(it, ignoreCase = true) }
    }

    fun sendMessage(question: String) {
        if (!isFitnessQuestion(question)) {
            // Notify the user that the question is not related to fitness
            messageList.add(MessageModel("Please ask a fitness-related question.", "model"))
            return
        }

        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )

                messageList.add(MessageModel(question, "user"))
                messageList.add(MessageModel("Typing....", "model"))

                val response = chat.sendMessage(question)
                messageList.removeLast()
                messageList.add(MessageModel(response.text.toString(), "model"))
            } catch (e: Exception) {
                messageList.removeLast()
                messageList.add(MessageModel("Error : " + e.message.toString(), "model"))
            }
        }
    }
}
