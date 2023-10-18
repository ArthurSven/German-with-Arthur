package com.devapps.germanwitharthur.Views.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.devapps.germanwitharthur.Data.Model.FirebaseAuth
import com.devapps.germanwitharthur.R
import com.devapps.germanwitharthur.Views.MainActivity
import com.devapps.germanwitharthur.Views.Users.UserHomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {

    private lateinit var authManager: FirebaseAuth
    private lateinit var handler: Handler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize authManager when the fragment is attached to the activity
        authManager = FirebaseAuth(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signup = view.findViewById<TextView>(R.id.signup)
        val login = view.findViewById<Button>(R.id.login)

        signup.setOnClickListener {
            val signUpFragment = SignupFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, signUpFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        login.setOnClickListener {
            val emailET = view.findViewById<EditText>(R.id.email)
            val passwordET = view.findViewById<EditText>(R.id.password)

            val email = emailET.text.toString()
            val password = passwordET.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()) {
                try {
                    CoroutineScope(Dispatchers.Main).launch {
                        val login = authManager.loginUser(email, password)

                        if (login != null) {
                            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                            handler = Handler()
                                handler.postDelayed({
                                    val intent = Intent(requireContext(), UserHomeActivity::class.java)
                                    startActivity(intent)
                                    requireActivity().finish()
                                }, 3000)
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all the forms", Toast.LENGTH_SHORT).show()
            }

        }


    }


}