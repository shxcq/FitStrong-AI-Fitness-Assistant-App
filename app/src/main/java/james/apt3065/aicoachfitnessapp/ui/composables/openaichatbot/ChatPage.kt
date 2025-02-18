@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import james.apt3056.aicoachfitnessapp.R
import james.apt3065.aicoachfitnessapp.ai.ChatViewModel
import james.apt3065.aicoachfitnessapp.ai.MessageModel

@Composable
fun ChatPage(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel()
) {
    Column(
        modifier = modifier.background(Color.Black)
    ) {
        AppHeader()
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = viewModel.messageList
        )
        MessageInput(
            onMessageSend = {
                viewModel.sendMessage(it)
            }
        )
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>) {
    if (messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription = "Icon",
                tint = Color(0xFF222222) // Icon color matching the second image
            )
            Text(text = "Ask me anything", fontSize = 22.sp, color = Color(0xFF222222))
        }
    } else {
        LazyColumn(
            modifier = modifier
                .background(Color.Black) // Background color matching the second image
                .padding(horizontal = 16.dp),
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.TopStart else Alignment.TopEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .background(
                        color = if (isModel) Color(0xFF292B2E) else Color(0xFFF7C927), // Dark gray for model, blue for user
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W500,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {
    var message by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .background(Color(0xFF333333)), // Background color matching the second image
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFF333333)), // Dark background
            value = message,
            onValueChange = {
                message = it
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFF7C927),
                unfocusedBorderColor = Color(0xFFF7C927),
                focusedLabelColor = Color(0xFFF7C927),
                unfocusedLabelColor = Color(0xFFF7C927),
                cursorColor = Color(0xFFF7C927),
            )
        )
        IconButton(onClick = {
            if (message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = Color(0xFF3D5AFE) // Blue color
            )
        }
    }
}

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black) // Dark background matching the second image
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "AI Coach",
            color = Color.White,
            fontSize = 22.sp
        )
    }
}
