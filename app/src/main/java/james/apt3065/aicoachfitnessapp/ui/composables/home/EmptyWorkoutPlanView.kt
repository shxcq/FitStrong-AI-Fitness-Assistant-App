package james.apt3065.aicoachfitnessapp.ui.composables.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import james.apt3065.aicoachfitnessapp.ui.composables.RegularButton
import james.apt3056.aicoachfitnessapp.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle

@Composable
fun EmptyWorkoutPlanView(modifier: Modifier = Modifier, user: String = "", onClick: () -> Unit = {}) {

    val currentHour = remember { LocalTime.now().hour }
    val greeting = when (currentHour) {
        in 0..11 -> stringResource(R.string.good_morning)
        in 12..17 -> stringResource(R.string.good_afternoon)
        else -> stringResource(R.string.good_evening)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.Start
    ) {
        Heading(text = "$greeting ${user.replaceFirstChar { it.uppercase() }}")

        SubHeading(text = stringResource(R.string.empty_plan_text))

        RegularButton(
            text = stringResource(R.string.get_started),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmptyWorkoutPlanView() {
    EmptyWorkoutPlanView(user = "John")
}