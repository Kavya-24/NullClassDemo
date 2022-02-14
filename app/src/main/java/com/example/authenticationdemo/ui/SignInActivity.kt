package com.example.authenticationdemo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authenticationdemo.MainActivity
import com.example.authenticationdemo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val TAG = SignInActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()


        this.apply {
            tv_signUp.setOnClickListener {
                goToSignUp()
            }
            account_sign_in.setOnClickListener {
                login()
            }

        }


    }


    private fun login() {

        this.apply {
            auth.signInWithEmailAndPassword(
                email_signIn.text.toString(),
                password_signIn.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.e(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, resources.getString(R.string.authFailed),
                            Toast.LENGTH_SHORT
                        ).show()


                    }


                }
        }


    }

    private fun goToSignUp() {
        val i = Intent(this, SignUpActivity::class.java)
        startActivity(i)
    }


    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            Log.e(TAG, "User Logged in. UID = " + currentUser.uid)
            goToMainActivity()
        }


    }

    private fun goToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finishAffinity()
    }

}
