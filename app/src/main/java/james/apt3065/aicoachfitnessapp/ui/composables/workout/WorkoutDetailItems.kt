import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import james.apt3056.aicoachfitnessapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.DismissDirection
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import james.apt3065.aicoachfitnessapp.data.models.ExerciseItem
import james.apt3065.aicoachfitnessapp.ui.composables.home.Heading
import james.apt3065.aicoachfitnessapp.ui.composables.home.Title
import james.apt3065.aicoachfitnessapp.ui.theme.veryDarkBlue
import james.apt3065.aicoachfitnessapp.viewmodel.WorkoutViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseItemsDisplay(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel
) {

    val exerciseList =
        workoutViewModel.workoutState.exerciseItems

    LazyColumn(modifier = modifier) {

        exerciseList?.let { exercises ->

            itemsIndexed(
                items = exercises,
                key = {index,item -> item.hashCode()+index+exercises.indexOf(item)}
               ) { index, data ->
                val dismissState = rememberDismissState()

                if (dismissState.targetValue == DismissValue.DismissedToEnd)
                    workoutViewModel.removeExercise(data)
                    workoutViewModel.getWorkouts()

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
                    },
                    background = {}
                ) {

                    ExerciseItem(data)

                }

            }

        }

    }


}


@Composable
fun ExerciseItem(data: ExerciseItem) {

    val exerciseName by remember { mutableStateOf(data.name) }
    val sets by remember { mutableStateOf(data.sets) }
    val equipment by remember { mutableStateOf(data.equipments) }

    Surface(
        color = veryDarkBlue,
        shape = RectangleShape,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(0.95f)
                    .padding(end = 30.dp)
            ) {


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Heading(text = exerciseName.toString().replaceFirstChar { it.uppercase() })
                    Heading(text = sets.toString())
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Title(text = stringResource(id = equipment?.name!!))
                    Title(text = stringResource(id = R.string.sets))
                }

            }

            Column(
                modifier = Modifier
                    .weight(0.1f)
                    .size(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                /* TODO: IMPLEMENT EDIT LOGIC
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = stringResource(R.string.edit),
                    tint = holoGreen,
                    modifier = Modifier.size(50.dp)

                )
                 */
            }


        }

        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(), color = Color.Gray.copy(0.1f)
        )

    }


}