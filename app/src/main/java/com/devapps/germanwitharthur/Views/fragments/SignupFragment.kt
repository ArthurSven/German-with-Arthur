package com.devapps.germanwitharthur.Views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.devapps.germanwitharthur.R

class SignupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = view.findViewById<TextView>(R.id.login)

        login.setOnClickListener {
            val authFragment = AuthFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, authFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}