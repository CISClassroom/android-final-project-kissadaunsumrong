package com.example.myapplication

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_data.*

class ListData : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    var toDoItemList: MutableList<ToDo>? = null
    lateinit var adapter: ToDoItemAdapter
    private var listViewItems: ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data)


        mDatabase = FirebaseDatabase.getInstance().reference
        listViewItems = findViewById<View>(R.id.listshow) as ListView

        toDoItemList = mutableListOf<ToDo>()
        adapter = ToDoItemAdapter(this, toDoItemList!!)
        listViewItems!!.setAdapter(adapter)
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

        listshow.setOnItemClickListener{ parent, view, position, id ->
            val intent = Intent(this@ListData,Showdetail::class.java)
            val selectedItem = parent.getItemAtPosition(position) as ToDo
            intent.putExtra("namesport",selectedItem.Namesport.toString())
            intent.putExtra("detail",selectedItem.Detail.toString())
            intent.putExtra("id",selectedItem.id.toString())
            startActivity(intent)



        }
    }


    var itemListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            addDataToList(dataSnapshot.child("Sport"))
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }


        private fun addDataToList(dataSnapshot: DataSnapshot) {
            val items = dataSnapshot.children.iterator()
            var email = getIntent().getStringExtra("email")
            // Check if current database contains any collection
            if (items.hasNext()) {

                // check if the collection has any to do items or not
                while (items.hasNext()) {
                    // get current item
                    val currentItem = items.next()
                    val map = currentItem.getValue() as HashMap<String,Any>
                    // add data to object

                        val todoItem = ToDo.create()
                        todoItem.Namesport = map.get("namesport") as String
                        todoItem.Detail = map.get("detail") as String
                        todoItem.id = map.get("id") as String
                        toDoItemList!!.add(todoItem);
                    }
                }
                adapter.notifyDataSetChanged()
        }
    }
}
