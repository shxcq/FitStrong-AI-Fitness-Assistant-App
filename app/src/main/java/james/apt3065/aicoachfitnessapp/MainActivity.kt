package james.apt3065.aicoachfitnessapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity // Use AppCompatActivity instead of ComponentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import james.apt3065.aicoachfitnessapp.data.service.WorkoutTimerService
import james.apt3065.aicoachfitnessapp.ui.navigation.RootNavGraph
import james.apt3065.aicoachfitnessapp.ui.theme.InFitTheme
import james.apt3065.aicoachfitnessapp.util.getTimeStringFromDouble
import james.apt3065.aicoachfitnessapp.viewmodel.UserViewModel
import james.apt3065.aicoachfitnessapp.viewmodel.WorkoutViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavHostController

    private val userViewModel: UserViewModel by viewModels()
    private val workoutViewModel: WorkoutViewModel by viewModels()
    private var timeElapsed = 0.0
    private lateinit var serviceIntent: Intent

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            timeElapsed = intent.getDoubleExtra(WorkoutTimerService.TIME_ELAPSED, 0.0)
            workoutViewModel.timerText = getTimeStringFromDouble(timeElapsed)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        serviceIntent = Intent(this, WorkoutTimerService::class.java)

        // Register the receiver with the specified export status
        registerReceiver(
            updateTime,
            IntentFilter(WorkoutTimerService.TIMER_UPDATED),
            Context.RECEIVER_NOT_EXPORTED // or Context.RECEIVER_EXPORTED if needed
        )

        setContent {
            val navController = rememberNavController() // Initialize navController

            InFitTheme {
                // Pass navController to your root navigation graph
                RootNavGraph(navController = navController, userViewModel = userViewModel, workoutViewModel = workoutViewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(serviceIntent)
        unregisterReceiver(updateTime)
    }
}
