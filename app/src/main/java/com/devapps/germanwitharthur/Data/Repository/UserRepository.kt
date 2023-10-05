package com.devapps.germanwitharthur.Data.Repository

import com.devapps.germanwitharthur.Data.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository{

    private val authManager: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.
    getInstance("https://learn-german-with-tuuri-default-rtdb.firebaseio.com")
        .getReference("Users")

    suspend fun userSignUp(username: String, email: String, password: String) {
        val result = authManager.createUserWithEmailAndPassword(email, password)
        try {
            if (result != null) {
                val user = User(username)
                database.child(username).setValue(user).await()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {

            }
        }

    }

    suspend fun userLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                 authManager.signInWithEmailAndPassword(email, password)
                withContext(Dispatchers.Main) {
                    return@withContext true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    return@withContext false
                }
            }
        }
    }

   suspend fun resetPassword(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                authManager.sendPasswordResetEmail(email).await()
                withContext(Dispatchers.Main) {
                    return@withContext true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    return@withContext false
                }
            }
        }
    }

}
