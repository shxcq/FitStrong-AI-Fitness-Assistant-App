package james.apt3065.aicoachfitnessapp.ui.composables

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.graphicsLayer
import james.apt3065.aicoachfitnessapp.ui.theme.darkBlue
import james.apt3065.aicoachfitnessapp.ui.theme.holoGreen
import james.apt3065.aicoachfitnessapp.ui.theme.outfit
import james.apt3065.aicoachfitnessapp.ui.theme.white

@Preview
@Composable
fun RegularButton(
    modifier: Modifier = Modifier,
    text: String = "button",
    textColor: Color = white,
    backgroundColor: Color = darkBlue,
    onClick: () -> Unit = {}
) {
    Surface(
        color = backgroundColor,
        shape = RectangleShape,  // No rounded corners
        shadowElevation = 8.dp,  // Increased shadow elevation
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
            .border(BorderStroke(2.dp, Color.Black.copy(alpha = 0.1f)), shape = RectangleShape)  // Subtle border
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(backgroundColor.copy(alpha = 0.8f), backgroundColor),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),  // Increased padding
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            ButtonText(
                text = text.uppercase(),
                color = textColor,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun FloatingAddButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .clip(RectangleShape)  // No rounded corners
            .size(64.dp)  // Slightly smaller size
            .background(holoGreen)
            .clickable { onClick() }
            .graphicsLayer {
                shadowElevation = 12.dp.toPx()  // Larger shadow elevation
                shape = RectangleShape
                clip = true
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Check,  // Changed icon
            contentDescription = null,
            tint = Color.White,  // Changed icon color
            modifier = Modifier.size(36.dp)  // Slightly smaller icon
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RoundedCheckBox(
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    var color by remember { mutableStateOf(Color.Transparent) }
    var checkedState by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(36.dp)  // Slightly larger size
            .clip(RectangleShape)  // No rounded corners
            .clickable {
                checkedState = !checkedState
                onCheckedChange(checkedState)
                color = if (checkedState) holoGreen else Color.Transparent
            }
            .border(BorderStroke(2.dp, if (checkedState) holoGreen else Color.Transparent), shape = RectangleShape)  // Animated border
            .graphicsLayer {
                shadowElevation = 6.dp.toPx()  // Increased shadow elevation
                shape = RectangleShape
                clip = true
            },
        contentAlignment = Alignment.Center
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = color) {
            AnimatedVisibility(
                visible = checkedState,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,  // Changed icon
                    contentDescription = null,
                    tint = darkBlue,
                    modifier = Modifier.size(24.dp)  // Slightly smaller icon
                )
            }
        }
    }
}

@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = white,
    fontWeight: FontWeight = FontWeight.Light
) {
    Text(
        text = text,
        fontWeight = fontWeight,
        fontFamily = outfit,
        fontSize = 18.sp,
        color = color,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color(0xFFF0F0F0)).padding(16.dp)
    ) {
        RegularButton(text = "Submit")
        FloatingAddButton()
        RoundedCheckBox()
    }
}
