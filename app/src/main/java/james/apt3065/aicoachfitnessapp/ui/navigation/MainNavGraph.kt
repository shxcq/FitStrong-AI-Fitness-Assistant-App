package james.apt3065.aicoachfitnessapp.ui.navigation

import ChatPage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import james.apt3065.aicoachfitnessapp.ui.composables.exercises.ExerciseDetailScreen

import james.apt3065.aicoachfitnessapp.ui.composables.home.HomeScreen

import james.apt3065.aicoachfitnessapp.ui.composables.stats.StatsDetailScreen
import james.apt3065.aicoachfitnessapp.ui.composables.stats.StatsScreen
import james.apt3065.aicoachfitnessapp.ui.composables.workout.WorkoutDetailScreen
import james.apt3065.aicoachfitnessapp.ui.composables.workout.WorkoutPlanSetUpScreen
import james.apt3065.aicoachfitnessapp.ui.composables.workout.WorkoutScreen
import james.apt3065.aicoachfitnessapp.ui.theme.holoGreen
import james.apt3065.aicoachfitnessapp.viewmodel.UserViewModel
import james.apt3065.aicoachfitnessapp.viewmodel.WorkoutViewModel

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    userViewModel: UserViewModel,
    workoutViewModel: WorkoutViewModel,
    scaffoldState: ScaffoldState
) {
    navigation(startDestination = Screens.Home.route, route = MAIN_ROUTE) {
        composable(route = Screens.Home.route) {
            HomeScreen(navController, userViewModel, workoutViewModel, scaffoldState)
            bottomBarState.value = true
        }
        composable(route = Screens.Stats.route) {
            StatsScreen(navController, workoutViewModel)
            bottomBarState.value = true
        }
        composable(route = Screens.Profile.route) {
            ChatPage()
            bottomBarState.value = true
        }
        composable(route = Screens.StatsDetails.route) {
            StatsDetailScreen(navController, workoutViewModel)
            bottomBarState.value = true
        }
        composable(route = Screens.WorkoutDetails.route) {
            WorkoutDetailScreen(navController, workoutViewModel)
            bottomBarState.value = true
        }
        composable(route = Screens.Workout.route) {
            WorkoutScreen(workoutViewModel, navController)
            bottomBarState.value = true
        }
        composable(route = Screens.ExerciseDetails.route) {
            ExerciseDetailScreen(Modifier, navController, workoutViewModel)
            bottomBarState.value = true
        }
        composable(route = Screens.WorkoutPlanSetUp.route) {
            WorkoutPlanSetUpScreen(workoutViewModel = workoutViewModel, navController)
            bottomBarState.value = true
        }
        }
    }
@Composable
fun BottomNavBar(
    navController: NavHostController = rememberNavController(),
    bottomBarState: MutableState<Boolean>
) {
    val screens = listOf(
        Screens.Home,
        Screens.Stats,
        Screens.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(
            modifier = Modifier.height(70.dp),
            elevation = 0.dp,
            backgroundColor = Color.Transparent
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = stringResource(id = screen.title))
        },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = { Icon(imageVector = screen.icon!!, contentDescription = null) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        selectedContentColor = holoGreen,
        unselectedContentColor = holoGreen.copy(alpha = ContentAlpha.disabled)
    )
}
