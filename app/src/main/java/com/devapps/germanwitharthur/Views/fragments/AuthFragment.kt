package com.devapps.germanwitharthur.Views.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.devapps.germanwitharthur.R
import com.devapps.germanwitharthur.Utils.UserUtils.clearUserData
import com.devapps.germanwitharthur.Utils.UserUtils.saveUserDetails
import com.devapps.germanwitharthur.Views.MainActivity
import com.devapps.germanwitharthur.Views.Users.UserHomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthFragment : Fragment() {

    private lateinit var authManager: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val PREFS_NAME = "UserPrefs"

   override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize authManager when the fragment is attached to the activity
       authManager = FirebaseAuth.getInstance()// Initialize FirebaseAuth
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authManager = FirebaseAuth.getInstance() // Initialize FirebaseAuth

        //Configure google sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        view.findViewById<MaterialCardView>(R.id.google).setOnClickListener {
            signInGoogle()
        }

    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account : GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        authManager.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                val displayName = account.displayName
                val displayPicture = account.photoUrl.toString()

                saveUserDetails(requireContext(), displayName, displayPicture)

                val intent = Intent(requireContext(), UserHomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInGoogle() {
       val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    fun signOut() {
        clearUserData(requireContext())
        authManager.signOut()
        // After signing out, navigate to MainActivity
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}