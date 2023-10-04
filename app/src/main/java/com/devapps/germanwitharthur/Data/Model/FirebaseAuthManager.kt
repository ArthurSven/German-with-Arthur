package com.devapps.germanwitharthur.Data.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthManager {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.
    getInstance("https://learn-german-with-tuuri-default-rtdb.firebaseio.com")
        .getReference("Users")

    suspend fun signupUsers(
        username: String,
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password)
                if (result != null) {
                    val user = User(username)
                    database.child(username).setValue(user).await()
                    withContext(Dispatchers.Main) {
                        onComplete(true, null)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onComplete(false, "User registration failed")
                    }
                }
            }  catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onComplete(false, e.message)
                }
            }
        }
    }
}