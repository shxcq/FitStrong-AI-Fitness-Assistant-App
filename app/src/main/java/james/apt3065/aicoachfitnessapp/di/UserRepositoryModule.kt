package james.apt3065.aicoachfitnessapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import james.apt3065.aicoachfitnessapp.data.repository.UserRepositoryImpl
import james.apt3065.aicoachfitnessapp.domain.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl):UserRepository

}
