package com.example.authenticationdemo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authenticationdemo.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val TAG = SignInActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        this.apply {
            tv_signIn.setOnClickListener {
                goToLoginActivity()
            }

            account_signUp.setOnClickListener {
                signUp()
            }
        }


    }


    private fun signUp() {
        if (validate()) {
            createUser()
        }


    }

    private fun createUser() {

        this.apply {
            auth.createUserWithEmailAndPassword(
                email_signUp.text.toString(),
                password_signUp.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        Log.e(TAG, "createUserWithEmail:success")

                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }


                }


        }

    }

    private fun updateUI(user: FirebaseUser?) {

        //Send UID
        if (user != null) {
            Log.e(TAG, "User Registered with " + user.uid)
            Toast.makeText(this, "SignUp successful", Toast.LENGTH_SHORT).show()
            goToLoginActivity()
        }

    }

    private fun goToLoginActivity() {
        val i = Intent(this, SignInActivity::class.java)
        startActivity(i)
        finish()

    }

    private fun validate(): Boolean {


        this.apply {
            var isValid = true
            if (email_signUp.text.isNullOrEmpty()) {
                isValid = false
                email_signUp.error = resources.getString(R.string.empEmail)
            } else {
                isValid = true
            }

            if (password_signUp.text.isNullOrEmpty()) {
                isValid = false
                password_signUp.error = resources.getString(R.string.empPassword)
            } else {
                isValid = true
            }

            return isValid
        }

    }
}
