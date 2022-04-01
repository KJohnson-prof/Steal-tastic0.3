package com.example.steal_tastic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_login)


        //Check if there's a user logged in
        //If there is, take them to MainActivity
        if(ParseUser.getCurrentUser() != null){
            goToMainActivity()

        }

        //Setting up an onClickListener for the login button
        findViewById<Button>(R.id.bt_login).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            loginUser(username, password)
        }

        //Setting up an onClickListener for the sign up button
        findViewById<Button>(R.id.bt_signUp).setOnClickListener{
            goToSignUpActivity()
        }
        //End of onCreate()
    }


    private fun loginUser(username: String, password: String) {
        //Background network call
        ParseUser.logInInBackground(username, password, ({user, e ->
            if(user != null){
                Log.i(TAG, "Successfully logged in user")
                goToMainActivity()
            }
            else{
                //Let user know they failed
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
            }
        }))
        //end of loginUser()
    }

    private fun signUpUser(username: String, password: String) {
        //Creating the ParseUser
        val user = ParseUser()

        //Setting the fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        //Was signup successful?
        user.signUpInBackground { e ->
            if (e == null) {
                Log.i(TAG, "Successfully signed up user")
                //Navigate the user to the MainActivity
                goToSignUpActivity()
            } else {
                //Show toast to indicate user success
                Toast.makeText(this, "Unsuccessful sign up", Toast.LENGTH_SHORT).show()

                //for the developer to see whats wrong
                e.printStackTrace()
            }

        }
        //End of signUpUser()
    }

    //Creating a method to enter main activity
    private fun goToMainActivity(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        //When user press back button, it would exit out of app instead of back to login page
        finish()
    }

    //Creating a method to enter SignUpActivity (tags)
    private fun goToSignUpActivity(){
        val intent = Intent(this@LoginActivity, AddFirstTagsActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object{
        const val TAG= "LoginActivity"
    }
}