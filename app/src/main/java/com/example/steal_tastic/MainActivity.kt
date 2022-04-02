package com.example.steal_tastic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.steal_tastic.fragments.ComposeFragment
import com.example.steal_tastic.fragments.FeedFragment
import com.example.steal_tastic.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
                item ->

            var fragmentToShow: Fragment? = null
            when(item.itemId){
                R.id.action_home -> {
                    //Navigate to the home screen / feed fragment
                    fragmentToShow = FeedFragment()
                }
                R.id.action_compose -> {
                    //Navigate to the compose screen
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_search -> {
                    fragmentToShow = SearchFragment()
                }
            }
            if(fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            // Return true to say that we've handled this user interaction on the item
            true
        }

        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home
    }
    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}

/** database & login/sign up activities (Liza)
 * -tell us api keys
 * -mandatory follow categories
 * main activity (Abdullah)
 * -feed of categories they follow
 * -task bar for navigation
 *post activity (Kristle)
 *search activity (Nina)
 * **/