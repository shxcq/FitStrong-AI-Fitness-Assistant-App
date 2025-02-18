package james.apt3065.aicoachfitnessapp.ui.composables.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.chargemap.compose.numberpicker.NumberPicker
import james.apt3056.aicoachfitnessapp.R
import james.apt3065.aicoachfitnessapp.data.models.WorkoutPlan
import james.apt3065.aicoachfitnessapp.ui.composables.RegularButton
import james.apt3065.aicoachfitnessapp.ui.composables.home.Heading
import james.apt3065.aicoachfitnessapp.ui.composables.home.SubHeading
import james.apt3065.aicoachfitnessapp.ui.composables.home.Title
import james.apt3065.aicoachfitnessapp.ui.navigation.Screens
import james.apt3065.aicoachfitnessapp.ui.theme.*
import james.apt3065.aicoachfitnessapp.util.DifficultyLevels
import james.apt3065.aicoachfitnessapp.util.DifficultyLevels.Companion.Advanced
import james.apt3065.aicoachfitnessapp.util.DifficultyLevels.Companion.Beginner
import james.apt3065.aicoachfitnessapp.util.DifficultyLevels.Companion.Intermediate
import james.apt3065.aicoachfitnessapp.viewmodel.WorkoutViewModel
import java.time.DayOfWeek

@Composable
fun WorkoutPlanSetUpScreen(workoutViewModel: WorkoutViewModel, navController: NavHostController) =
    with(workoutViewModel) {
        var workoutPlanName by remember { mutableStateOf("") }
        var duration by remember { mutableStateOf(0) }

        // Background gradient using a Box
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E1E2F), Color(0xFF232A4A))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.set_up_workout_plan_heading),
                    modifier = Modifier.padding(start = 8.dp, top = 20.dp),
                    fontSize = 24.sp,
                    color = Color.White
                )

                Text(
                    text = stringResource(R.string.choose_a_name),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
                TextField(
                    value = workoutPlanName,
                    onValueChange = { workoutPlanName = it },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(
                            Color.White.copy(alpha = 0.1f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.White)
                )

                Text(
                    text = stringResource(R.string.select_training_days),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    fontSize = 16.sp,
                    color = Color.LightGray
                )

                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(DayOfWeek.values().toList()) { day ->
                        WeekdaysChipItem(day = day, onCheck = { day, checked ->
                            if (checked) addDay(day) else removeDay(day)
                        })
                    }
                }

                Text(
                    text = stringResource(R.string.difficulty_level),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    fontSize = 16.sp,
                    color = Color.LightGray
                )

                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(listOf(Beginner, Intermediate, Advanced)) { level ->
                        DifficultyLevelItems(
                            difficulty = level,
                            onCheck = { selectDifficulty(it) },
                            checked = selectedDifficulty == level
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.duration),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    fontSize = 16.sp,
                    color = Color.LightGray
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    NumberPicker(
                        value = duration,
                        onValueChange = { duration = it },
                        range = 10..120,
                        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                        dividersColor = Color(0xFF66BB6A),
                        modifier = Modifier
                            .background(
                                Color.White.copy(alpha = 0.1f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .width(120.dp)
                    )
                    Text(
                        text = stringResource(R.string.minutes),
                        color = Color.LightGray,
                        fontSize = 18.sp
                    )
                }

                Button(
                    onClick = {
                        if (workoutPlanName.isNotEmpty() && selectedDays.isNotEmpty()) {
                            val workoutPlan = WorkoutPlan(
                                name = workoutPlanName,
                                workouts = ArrayList(selectedDays.toList()),
                                difficulty = selectedDifficulty,
                                duration = duration
                            )
                            addWorkoutPlan(workoutPlan)
                            navController.navigate(Screens.Home.route)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF66BB6A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

@Composable
fun WeekdaysChipItem(
    modifier: Modifier = Modifier,
    onCheck: (DayOfWeek, Boolean) -> Unit,
    day: DayOfWeek
) {
    val text = day.name.substring(0..1).uppercase()
    var checked by remember { mutableStateOf(false) }
    val backgroundColor = if (checked) Color(0xFF66BB6A) else Color.White.copy(0.1f)
    val textColor = if (checked) Color.Black else Color.LightGray

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .clickable {
                checked = !checked
                onCheck(day, checked)
            }
            .size(60.dp)
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = text, color = textColor, fontSize = 18.sp)
        }
    }
}

@Composable
fun DifficultyLevelItems(
    difficulty: DifficultyLevels.Difficulty,
    onCheck: (DifficultyLevels.Difficulty) -> Unit,
    checked: Boolean
) {
    val backgroundColor = if (checked) Color(0xFF66BB6A) else Color.White.copy(0.1f)
    val textColor = if (checked) Color.Black else Color.LightGray

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .clickable { onCheck(difficulty) }
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = difficulty.difficulty ?: R.string.difficulty_level),
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}
