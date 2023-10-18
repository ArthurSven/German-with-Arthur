package com.devapps.germanwitharthur.Views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.devapps.germanwitharthur.Data.Model.FirebaseAuth
import com.devapps.germanwitharthur.R
import com.devapps.germanwitharthur.Views.Users.UserHomeActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var sharedPreferences: SharedPreferences // Initialize SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val authManager = FirebaseAuth(applicationContext) //
        handler = Handler()


        // Check if the user is already logged in
        if (authManager.isUserLoggedIn()) {
            // If the user is logged in, navigate to UserHomeActivity
            val intent = Intent(this, UserHomeActivity::class.java)
            startActivity(intent)
        } else {
            // If the user is not logged in, navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        finish()
    }//Delay time for activity
}