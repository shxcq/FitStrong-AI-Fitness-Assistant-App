package james.apt3065.aicoachfitnessapp.data.models.states

import james.apt3065.aicoachfitnessapp.data.models.WorkoutPlan

data class WorkoutPlanState(
    val workoutPlan:WorkoutPlan? = null,
    val loading:Boolean = false,
    val error:String? = null
)
