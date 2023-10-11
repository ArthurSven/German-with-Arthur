package com.devapps.germanwitharthur.Views.Users

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.devapps.germanwitharthur.R
import com.devapps.germanwitharthur.Views.Users.fragments.ClientHomeFragment
import com.devapps.germanwitharthur.Views.Users.fragments.ProgressFragment
import com.devapps.germanwitharthur.Views.Users.fragments.SettingsFragment
import com.devapps.germanwitharthur.Views.fragments.HomeFragment
import com.google.android.material.navigation.NavigationView

class UserHomeActivity : AppCompatActivity() {
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
            }
            true
        }
    }

    private fun setDrawerItem(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.germanFrame, fragment)
            .commit()
        return true
    }

}