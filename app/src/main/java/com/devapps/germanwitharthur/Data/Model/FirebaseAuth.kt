package com.devapps.germanwitharthur.Data.Model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class FirebaseAuth(private val context: Context) {

    private val authManager: FirebaseAuth = FirebaseAuth.getInstance()

    private val database: DatabaseReference = FirebaseDatabase.
    getInstance("https://learn-german-with-tuuri-default-rtdb.firebaseio.com")
        .getReference("Users")

    // SharedPreferences for session management
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    private val sessionEditor: SharedPreferences.Editor = sharedPreferences.edit()


    suspend fun userSignUp(username: String, email: String, password: String) {
        val signup = authManager.createUserWithEmailAndPassword(email, password)
        if (signup.isSuccessful) {
            CoroutineScope(Dispatchers.Main).launch {
                val user = User(username)
                val userKey = database.push().key
                if (userKey != null) {
                    database.child(userKey).setValue(user).await()
                }

                sessionEditor.putString("userId", authManager.currentUser?.uid)
                sessionEditor.apply()
            }
        }
    }

    suspend fun loginUser(email: String, password: String) {
        try {
            val login = authManager.signInWithEmailAndPassword(email, password)
            if (login.isSuccessful) {
                CoroutineScope(Dispatchers.Main).launch {
                    sessionEditor.putString("userId", authManager.currentUser?.uid)
                    sessionEditor.apply()
                    true
                }
            } else {
                 false
            }
        } catch (e: Exception) {
            e.message?.let { Log.d("TAG", it, ) }
        }
    }

    fun isUserLoggedIn(): Boolean {
        // Check if the user is logged in by verifying the presence of the user's UID in SharedPreferences
        return sharedPreferences.getString("userId", null) != null
    }

}