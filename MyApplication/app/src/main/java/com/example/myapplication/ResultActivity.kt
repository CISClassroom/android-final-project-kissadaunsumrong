package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var  mAuthListener:FirebaseAuth.AuthStateListener? = null
    private val TAG: String = "Result Activity"

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        val users = mAuth!!.currentUser
        emailshow.text = users!!.email


        val spinner: Spinner = findViewById(R.id.spinner)
        val arrayList: ArrayList<String> = ArrayList()
        arrayList.add("เลือกห้อง")
        arrayList.add("ห้องประชุม")
        arrayList.add("โรงยิม")
        arrayList.add("ฟิตเนส")
        arrayList.add("ห้องคอมพิวเตอร์")
        arrayList.add("ห้องเรียน")
        val arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayList)
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter


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
            Log.d(TAG,"Email was empty!!")
            startActivity(Intent(this@ResultActivity,LoginActivity::class.java))
            finish()
        }
        button2.setOnClickListener{
            savedata()
            startActivity(Intent(this@ResultActivity,ListData::class.java))
            finish()
        }

    }

    private  fun savedata(){
        var Namesport = spinner.selectedItem.toString().trim()
        var Detail = editText2.text.toString().trim()
        var  todoItem = ToDo.create()

        val newItem = mDatabase.child("Sport").push()
        todoItem.id = newItem.key
        todoItem.Namesport = Namesport
        todoItem.Detail = Detail
        newItem.setValue(todoItem)
        Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show()
        finish()



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