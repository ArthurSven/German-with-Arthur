package com.devapps.germanwitharthur.Views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.devapps.germanwitharthur.Data.Repository.UserRepository
import com.devapps.germanwitharthur.R
import com.devapps.germanwitharthur.ViewModels.UserViewModel
import com.devapps.germanwitharthur.ViewModels.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        val userRepository = UserRepository() // Initialize your UserRepository here
        val viewModelFactory = UserViewModelFactory(userRepository)
        val userViewModel = ViewModelProvider(this, viewModelFactory)
            .get(UserViewModel::class.java)

        val login = view.findViewById<TextView>(R.id.login)
        val signup = view.findViewById<Button>(R.id.signup1)

        signup.setOnClickListener {
            val usernameEt = view.findViewById<EditText>(R.id.username)
            val emailEi = view.findViewById<EditText>(R.id.email)
            val passwordEt = view.findViewById<EditText>(R.id.password)

            val username = usernameEt.text.toString()
            val email = emailEi.text.toString()
            val password = passwordEt.text.toString()

            CoroutineScope(Dispatchers.Main).launch {
                try {
                  var success = userViewModel.userSignUp(username, email, password)

                    if (success != null) {
                        Toast.makeText(requireContext(), "${username}'s account has been created.",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "${username}'s account did not create.",
                            Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "$e occurred while creating $username's account.",
                        Toast.LENGTH_LONG).show()
                }

            }
        }

        login.setOnClickListener {
            val authFragment = AuthFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, authFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}