package com.example.go_go_taxy

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        val currentUser = auth.currentUser

        // Email Field
        val emailEditText = findViewById<EditText>(R.id.emailEditText)

        // Password Field
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        // Login Button
        val loginButton = findViewById<Button>(R.id.loginButton)

        // Forgot Password
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)

        // Sign Up Link
        val signupTextView = findViewById<TextView>(R.id.signupTextView)

        if(currentUser!=null){
            startActivity(Intent(this,front_page::class.java))

        }


        // Login Button Click Listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            when {
                email.isEmpty() -> {
                    emailEditText.error = "Please enter your email"
                    emailEditText.requestFocus()
                }
                password.isEmpty() -> {
                    passwordEditText.error = "Please enter your password"
                    passwordEditText.requestFocus()
                }
                else -> {
                    // Successful Login Login


                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success")
                                val user = auth.currentUser

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()

                            }
                        }


                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                    // Navigate to next activity (example: front_page activity)

                    startActivity(Intent(this, front_page::class.java))
                }
            }
        }

        // Sign Up TextView Click Listener
        signupTextView.setOnClickListener {
            // Navigate to Sign Up page (example: SignupActivity)
            startActivity(Intent(this, sign_up::class.java))
        }
    }
}
