package james.apt3065.aicoachfitnessapp.ui.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import james.apt3065.aicoachfitnessapp.data.models.WorkoutPlan
import james.apt3065.aicoachfitnessapp.ui.theme.*
import james.apt3065.aicoachfitnessapp.util.DifficultyLevels
import james.apt3056.aicoachfitnessapp.R

@Composable
fun WorkoutInfo(
    modifier: Modifier = Modifier,
    duration: String,
    difficulty: DifficultyLevels.Difficulty
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_timer),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$duration min",
            color = Color.White,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            painter = painterResource(id = difficulty.icon!!),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = difficulty.difficulty!!),
            color = Color.White,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun RestDayView(modifier: Modifier = Modifier) {

    Surface(
        color = Color.Transparent,
        modifier = modifier.fillMaxSize(),
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.no_sessions_text),
                style = MaterialTheme.typography.headlineSmall,
                color = veryDarkBlue,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            Text(
                text = stringResource(R.string.rest_view_text),
                style = MaterialTheme.typography.bodyLarge,
                color = veryDarkBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
