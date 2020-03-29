package com.example.myapplication

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var  mAuthListener:FirebaseAuth.AuthStateListener? = null
    private val TAG: String = "Result Activity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        mAuth = FirebaseAuth.getInstance()
        val users = mAuth!!.currentUser
        emailshow.text = users!!.email
        passshow.text = users.uid


        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val users = firebaseAuth.currentUser
            if (users == null){
                startActivity(Intent(this@ResultActivity,LoginActivity::class.java))
                finish()
            }
        }

        btn_signoutt.setOnClickListener{
            mAuth!!.signOut()
            Toast.makeText(this,"Signed out", Toast.LENGTH_LONG).show()
            Log.d(TAG,"Emaul was empty!!")
            startActivity(Intent(this@ResultActivity,LoginActivity::class.java))
            finish()
        }

    }
    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener { mAuthListener }
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener !=null) {
            mAuth!!.removeAuthStateListener { mAuthListener }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true)
        }
        return super.onKeyDown(keyCode, event)
    }
}