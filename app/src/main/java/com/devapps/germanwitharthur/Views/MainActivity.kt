package com.devapps.germanwitharthur.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.devapps.germanwitharthur.R
import com.devapps.germanwitharthur.Views.fragments.AuthFragment
import com.devapps.germanwitharthur.Views.fragments.HomeFragment
import com.devapps.germanwitharthur.Views.fragments.TranslateFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Fragments
        val homeFragment = HomeFragment()
        val authFragment = AuthFragment()
        val translateFragment = TranslateFragment()

        //Instantiating of bottom navigation
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigationView)

        //Setting default fragment when this activity is created
        setCurrentFragment(homeFragment)

        /*setting fragments order on the bottom navigation as well
        as ensuring icon corresponds to fragment
         */
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.auth -> setCurrentFragment(authFragment)
                R.id.translator -> setCurrentFragment(translateFragment)
            }; true
        }


    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}