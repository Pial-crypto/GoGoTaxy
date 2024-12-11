package com.example.go_go_taxy

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class sign_up : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    // Declare variables for all views
    private lateinit var scrollView: ScrollView
    private lateinit var signupTitle: TextView
    private lateinit var nameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var maleOption: RadioButton
    private lateinit var femaleOption: RadioButton
    private lateinit var otherOption: RadioButton
    private lateinit var termsCheckbox: CheckBox
    private lateinit var signupButton: Button

    // Declare ProgressDialog variable
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        // Edge to edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize all views
        scrollView = findViewById(R.id.main)
        signupTitle = findViewById(R.id.signup_title)
        nameInput = findViewById(R.id.name_input)
        phoneInput = findViewById(R.id.phone_input)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        confirmPasswordInput = findViewById(R.id.confirm_password_input)
        genderGroup = findViewById(R.id.gender_group)
        maleOption = findViewById(R.id.male_option)
        femaleOption = findViewById(R.id.female_option)
        otherOption = findViewById(R.id.other_option)
        termsCheckbox = findViewById(R.id.terms_checkbox)
        signupButton = findViewById(R.id.signup_button)

        // Initialize ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Signing Up...")  // Message to show while signing up
        progressDialog.setCancelable(false)  // Prevent cancellation

        signupButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()
            val selectedGenderId = genderGroup.checkedRadioButtonId
            val gender = when (selectedGenderId) {
                R.id.male_option -> "Male"
                R.id.female_option -> "Female"
                R.id.other_option -> "Other"
                else -> "Not selected"
            }
            val isTermsChecked = termsCheckbox.isChecked

            if (validateInputs(name, phone, email, password, confirmPassword, gender, isTermsChecked)) {
                // Show progress dialog when the sign up is in progress
                progressDialog.show()
                signUp(name, phone, email, password, confirmPassword, gender)
            }
        }
    }

    private fun validateInputs(
        name: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String,
        gender: String,
        isTermsChecked: Boolean
    ): Boolean {
        if (name.isEmpty()) {
            nameInput.error = "Name is required"
            nameInput.requestFocus()
            return false
        }
        if (phone.isEmpty()) {
            phoneInput.error = "Phone number is required"
            phoneInput.requestFocus()
            return false
        }
        if (phone.length != 11 && phone[0] != '0' && phone[1] != '1' && phone[2] != '7') {
            phoneInput.error = "Enter a valid phone number starting with 017"
            phoneInput.requestFocus()
            return false
        }
        if (email.isEmpty()) {
            emailInput.error = "Email is required"
            emailInput.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            passwordInput.error = "Password is required"
            passwordInput.requestFocus()
            return false
        }
        if (password != confirmPassword) {
            confirmPasswordInput.error = "Passwords do not match"
            confirmPasswordInput.requestFocus()
            return false
        }
        if (gender == "Not selected") {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!isTermsChecked) {
            Toast.makeText(this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String,
        gender: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "Registration successful!", Toast.LENGTH_SHORT).show()

                    // Save user data to Firebase Realtime Database
                    saveUserToFirebase(name, phone, email, gender)

                    // Hide the progress dialog after sign-up completion
                    progressDialog.dismiss()

                    // Navigate to next activity
                    startActivity(Intent(this, front_page::class.java))
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Registration failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                    // Hide the progress dialog if signup fails
                    progressDialog.dismiss()
                }
            }
    }

    private fun saveUserToFirebase(name: String, phone: String, email: String, gender: String) {
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users").push()

        val userData = User(name, phone, email, gender)
        userRef.setValue(userData)
            .addOnSuccessListener {
                Log.d(TAG, "User data saved to Firebase")
            }
            .addOnFailureListener {
                auth.currentUser!!.delete()
                Log.e(TAG, "Error saving user data to Firebase", it)
            }
    }

    // Data model for user
    data class User(val name: String, val phone: String, val email: String, val gender: String)
}
