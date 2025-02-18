package james.apt3065.aicoachfitnessapp.ui.composables.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import james.apt3065.aicoachfitnessapp.ui.composables.home.Heading
import james.apt3065.aicoachfitnessapp.ui.composables.home.SubHeading
import james.apt3065.aicoachfitnessapp.ui.navigation.Screens
import james.apt3065.aicoachfitnessapp.ui.theme.holoGreen
import james.apt3065.aicoachfitnessapp.ui.theme.white
import james.apt3065.aicoachfitnessapp.viewmodel.WorkoutViewModel
import james.apt3056.aicoachfitnessapp.R

@Preview
@Composable
fun StatsScreen(
    navController: NavHostController = rememberNavController(),
    workoutViewModel: WorkoutViewModel = viewModel()
) = with(workoutViewModel) {

    getHistoryData()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            modifier = Modifier.padding(start = 20.dp, top = 30.dp, end = 20.dp)
        ) {
            Heading(text = stringResource(R.string.your_stats))
            LazyColumn(
                modifier = Modifier
                    .padding(end = 40.dp, top = 25.dp)
                    .fillMaxHeight(),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(statsExercises.toList()) { exercise ->
                    StatsItem(exercise) {
                        navController.navigate(Screens.StatsDetails.route)
                        workoutViewModel.currentExerciseId = exercise
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun StatsItem(exercise: String, onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium,
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 15.dp)
        ) {
            SubHeading(text = exercise, color = MaterialTheme.colors.onSurface)
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = holoGreen,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}