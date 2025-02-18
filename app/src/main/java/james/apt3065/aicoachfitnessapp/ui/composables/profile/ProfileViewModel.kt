package james.apt3065.aicoachfitnessapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import james.apt3065.aicoachfitnessapp.model.UserProfile

class ProfileViewModel : ViewModel() {

    // LiveData holding the user's profile data
    val userName = MutableLiveData<String>()
    val userHeight = MutableLiveData<String>()
    val userWeight = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()

    // List holding profile settings options
    val profileSettingsOptions = listOf("Change Email", "Change Password", "Manage Subscriptions")

    // Firebase Firestore instance
    private val firestore = FirebaseFirestore.getInstance()

    // Firebase Auth instance
    private val auth = FirebaseAuth.getInstance()

    // Fetch the user's profile settings from Firestore
    fun getProfileSettings() {
        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    val userProfile = documentSnapshot.toObject(UserProfile::class.java)
                    userProfile?.let {
                        userName.value = it.name
                        userHeight.value = it.height
                        userWeight.value = it.weight
                        userEmail.value = it.email
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle any errors that occur while fetching data
                    exception.printStackTrace()
                }
        }
    }
}
