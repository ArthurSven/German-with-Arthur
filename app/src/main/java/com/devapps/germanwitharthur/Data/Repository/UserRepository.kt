package com.devapps.germanwitharthur.Data.Repository

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import com.devapps.germanwitharthur.Data.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    @SuppressLint("SuspiciousIndentation")
    suspend fun userSignUp(
        username: String,
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit) {
        val signUp = authManager.createUserWithEmailAndPassword(email, password)
            if (signUp != null) {
                try {
                    CoroutineScope(Dispatchers.IO).launch {
                        val user = User(username)
                        database.child(username).setValue(user).await()
                        withContext(Dispatchers.Main) {
                            onComplete(true, null)
                        }
                    }

                } catch (e: Exception) {
                    CoroutineScope(Dispatchers.IO).launch {
                        e.message?.let { Log.d(TAG, it) }
                    }
                }
            }


        }


    suspend fun userLogin(email: String, password: String, onComplete: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResult = authManager.signInWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main) {
                    onComplete(authResult !=null)
                }
            } catch (e: Exception) {
              withContext(Dispatchers.Main) {
                  onComplete(false)
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

    suspend fun getUserCurrentID(): String? {
        return authManager.currentUser!!.displayName
    }

}
