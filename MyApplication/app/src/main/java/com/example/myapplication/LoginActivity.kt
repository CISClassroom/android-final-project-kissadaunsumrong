package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Login Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null) {
            startActivity(Intent(this@LoginActivity,ResultActivity::class.java))
            finish()
        }
        button3.setOnClickListener {
            val email = email_edittxt.text.toString().trim { it <= ' ' }
            val password = password_edittxt.text.toString().trim { it <= ' ' }

            if (email.isEmpty()) {
                Toast.makeText(this,"please enter your email adedress",Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this,"please enter your email adedress",Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Authentication Failed:" + task.exception,
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d(TAG, "Enter password less than 6 characters." + task.exception)
                    } else {
                        Toast.makeText(this, "sign in successfully!", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Sign in successfully")
                        startActivity(Intent(this@LoginActivity,ResultActivity::class.java))
                        finish()
                    }
                }


        }
    }
}