package com.example.authenticationdemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authenticationdemo.ui.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser?.uid == null) {
            logout()
        } else {
            Toast.makeText(
                this,
                "Logged in. CurrentUser Email: {${auth.currentUser!!.email}}",
                Toast.LENGTH_LONG
            ).show()
        }

    }


    //Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.top_menu, menu)

        return true

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_logout -> logout()


            else ->
                return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        goToLoginActity()
        finish()
        finishAffinity()
    }

    private fun goToLoginActity() {
        val i = Intent(this, SignInActivity::class.java)
        startActivity(i)

    }

}
