package com.agremo.loan_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.agremo.loan_app.R

class detail : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

       val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentnavigationview ) as NavHostFragment
        navController = navHostFragment.navController
    }
}