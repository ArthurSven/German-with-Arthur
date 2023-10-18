package com.devapps.germanwitharthur.Views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.devapps.germanwitharthur.Data.Model.FirebaseAuth
import com.devapps.germanwitharthur.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    private lateinit var authManager: FirebaseAuth
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize authManager when the fragment is attached to the activity
        authManager = FirebaseAuth(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = view.findViewById<TextView>(R.id.login1)
        val signup = view.findViewById<Button>(R.id.signup1)

        login.setOnClickListener {
            val authFragment = AuthFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, authFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        signup.setOnClickListener {
            val usernameEt = view.findViewById<EditText>(R.id.username)
            val emailEi = view.findViewById<EditText>(R.id.email)
            val passwordEt = view.findViewById<EditText>(R.id.password)

            val username = usernameEt.text.toString()
            val email = emailEi.text.toString()
            val password = passwordEt.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val register = authManager.userSignUp(username, email, password)
                        if (register != null) {
                            Toast.makeText(requireContext(), "$username's account has been created",
                                Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all forms", Toast.LENGTH_SHORT).show()
            }




        }

    }
}