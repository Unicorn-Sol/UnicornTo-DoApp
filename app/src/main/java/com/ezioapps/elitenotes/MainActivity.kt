package com.ezioapps.elitenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragment),null)


    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() ||  NavigationUI.navigateUp(findNavController(R.id.fragment),null)
    }


}