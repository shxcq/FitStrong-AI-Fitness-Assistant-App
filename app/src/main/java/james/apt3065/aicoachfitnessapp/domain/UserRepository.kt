package james.apt3065.aicoachfitnessapp.domain

import com.google.firebase.auth.AuthResult
import james.apt3065.aicoachfitnessapp.util.Resource

interface UserRepository {

    suspend fun createNewUser(
        userName: String,
        userEmailAddress: String,
        userLoginPassword: String
    ): Resource<AuthResult>


    suspend fun loginUser(email: String, password: String): Resource<AuthResult>

    suspend fun logOutUser()
}