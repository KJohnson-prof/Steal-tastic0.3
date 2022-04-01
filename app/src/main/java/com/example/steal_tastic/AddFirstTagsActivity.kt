package com.example.steal_tastic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.ParseUser

class AddFirstTagsActivity() : AppCompatActivity() {
    val user = ParseUser()

    var listOfTags= mutableListOf<String>()

    //for changing the data
    lateinit var adapter:TagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar()?.hide();
        setContentView(R.layout.add_first_tags_screen)

        //Set up the buttons that are in the xml file

        //Allowing users to remove tags that they added (only in the signup stage)
        val OnLongClickListener = object:TagAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int){
                listOfTags.removeAt(position)
                //notify the adapter that data set changed
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }

        //Look up recyclerView in the layout and grab reference to it
        val recyclerView = findViewById<RecyclerView>(R.id.rv_tagList)

        //create adapter passing in the sample user data
        adapter = TagAdapter(listOfTags, OnLongClickListener)

        //attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField=findViewById<EditText>(R.id.et_typeTag)
        findViewById<Button>(R.id.bt_addTags).setOnClickListener{
            val userInputtedTag= inputTextField.text.toString()
            listOfTags.add(userInputtedTag)
            adapter.notifyItemInserted(listOfTags.size-1)
            inputTextField.setText("")
            saveItems()
        }

        //If the user presses skip then just bring them to the main activity
        findViewById<Button>(R.id.bt_skipTags).setOnClickListener{
            goToMainActivity()
        }

        //If the user presses done then save all of the tags to back4app and then go to the MainActivity
        findViewById<Button>(R.id.bt_done).setOnClickListener{



        }


    }

    //Adding the tags into parse
    fun saveItems(){


    }

    //Creating a method to enter main activity
    private fun goToMainActivity(){
        val intent = Intent(this@AddFirstTagsActivity, MainActivity::class.java)
        startActivity(intent)
        //When user press back button, it would exit out of app instead of back to login page
        finish()
    }



    companion object{
        const val TAG= "AddFirstTagsActivity"
    }
}