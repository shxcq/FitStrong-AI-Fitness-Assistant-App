package james.apt3065.aicoachfitnessapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import james.apt3065.aicoachfitnessapp.data.repository.WorkoutRepositoryImpl
import james.apt3065.aicoachfitnessapp.domain.WorkoutRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkoutRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository (workoutRepositoryImpl: WorkoutRepositoryImpl): WorkoutRepository
}