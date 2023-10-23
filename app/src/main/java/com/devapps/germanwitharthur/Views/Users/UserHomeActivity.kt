package com.devapps.germanwitharthur.Views.Users

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.devapps.germanwitharthur.R
import com.devapps.germanwitharthur.Utils.UserUtils.clearUserData
import com.devapps.germanwitharthur.Utils.UserUtils.getUserDetails
import com.devapps.germanwitharthur.Views.MainActivity
import com.devapps.germanwitharthur.Views.Users.fragments.ClientHomeFragment
import com.devapps.germanwitharthur.Views.Users.fragments.ProgressFragment
import com.devapps.germanwitharthur.Views.Users.fragments.SettingsFragment
import com.devapps.germanwitharthur.Views.fragments.HomeFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class UserHomeActivity : AppCompatActivity() {
    private val authManager = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        setDrawerItem(ClientHomeFragment())


        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toggle = findViewById<ImageView>(R.id.menus)
        val navView = findViewById<NavigationView>(R.id.navView)

        //setting toggle to open drawer navigation
        toggle.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.clientHome -> setDrawerItem(ClientHomeFragment())
                R.id.progress -> setDrawerItem(ProgressFragment())
                R.id.settings -> setDrawerItem(SettingsFragment())
                R.id.logout -> { signOut() }
            }
            true
        }

        val headerView = navView.getHeaderView(0)
        val drawerUsername = headerView.findViewById<TextView>(R.id.drawerUsername)
        val drawerProfilePic = headerView.findViewById<ImageView>(R.id.drawerPicture)

         val userDetails = getUserDetails(this)
        val displayName = userDetails.first
        val displayPicture = userDetails.second

        if (displayName != null) {
            drawerUsername.text = displayName
        }

        if (displayPicture != null) {
            Glide.with(this)
                .load(displayPicture)
                .into(drawerProfilePic)
        }
    }

    private fun setDrawerItem(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.germanFrame, fragment)
            .commit()
        return true
    }

    private fun signOut() {

        authManager.signOut() // Sign the user out

    //clear sharedPReferences
        // Clear user-related data from SharedPreferences
        clearUserData(this)

        // After signing out, you might want to navigate to the login or sign-in screen.
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close the UserHomeActivity
    }

}