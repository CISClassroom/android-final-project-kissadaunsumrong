package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_showdetail.*

class Showdetail : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showdetail)

        mDatabase = FirebaseDatabase.getInstance().reference

        var namesport = getIntent().getStringExtra("namesport")
        var detail = getIntent().getStringExtra("detail")
        var id = getIntent().getStringExtra("id")
        textView8.text = namesport
        textView11.text = detail



        button4.setOnClickListener{
            mDatabase.child("Sport").child(id).removeValue()
            Toast.makeText(this,"Delect Successful",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Showdetail,LoginActivity::class.java))
            finish()
        }
    }

}
