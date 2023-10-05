package com.devapps.germanwitharthur.Views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.devapps.germanwitharthur.Data.Repository.UserRepository
import com.devapps.germanwitharthur.R
import com.devapps.germanwitharthur.ViewModels.UserViewModel
import com.devapps.germanwitharthur.ViewModels.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userRepository = UserRepository() // Initialize your UserRepository here
        val viewModelFactory = UserViewModelFactory(userRepository)
        val userViewModel = ViewModelProvider(this, viewModelFactory)
            .get(UserViewModel::class.java)

        val signup = view.findViewById<TextView>(R.id.signup)
        val login = view.findViewById<Button>(R.id.login)

        login.setOnClickListener {
            val emailET = view.findViewById<EditText>(R.id.email)
            val passwordET = view.findViewById<EditText>(R.id.password)

            val email = emailET.text.toString()
            val password = passwordET.text.toString()

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    userViewModel.userLogin(email, password)
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                } catch (e:Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        signup.setOnClickListener {
            val signupFragment = SignupFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, signupFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

}