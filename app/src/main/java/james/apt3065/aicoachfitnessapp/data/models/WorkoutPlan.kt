package james.apt3065.aicoachfitnessapp.data.models

import james.apt3065.aicoachfitnessapp.util.DifficultyLevels
import java.time.DayOfWeek

data class WorkoutPlan (
    val name:String? = null,
    val workouts:ArrayList<DayOfWeek>? = null,
    val difficulty: DifficultyLevels.Difficulty?  = null,
    val duration:Int? = null,
)
