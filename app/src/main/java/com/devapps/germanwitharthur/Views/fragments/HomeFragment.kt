package com.devapps.germanwitharthur.Views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.devapps.germanwitharthur.R
import java.util.Calendar

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the current time
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        // Determine the greeting based on the time
        val greeting = when {
            hourOfDay > 4 -> "Good morning!"
            hourOfDay > 11 -> "Good afternoon!"
            else -> "Good evening!"
        }

        val greetingText = view.findViewById<TextView>(R.id.greeting)
        greetingText.text = greeting
    }


}