package com.agremo.loan_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.agremo.loan_app.*
import com.agremo.loan_app.data.notification
import com.agremo.loan_app.databinding.ActivityMainBinding
import com.agremo.loan_app.fragment.Profilefragment
import com.agremo.loan_app.fragment.helpfragment
import com.agremo.loan_app.fragment.homefragment
import com.agremo.loan_app.fragment.mywalletfragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replacefragment(homefragment())

        binding.bottomNavBar.setOnItemSelectedListener{ item ->
            when (item.itemId)
            {

                R.id.home ->
                {
                    replacefragment(homefragment())
                }

                R.id.howitworks -> {
                    replacefragment(mywalletfragment())
                }

                R.id.profile -> {
                    replacefragment(Profilefragment())
                }

                R.id.help -> {

                    replacefragment(helpfragment())
                }

            }

            return@setOnItemSelectedListener true
        }



    }

     fun replacefragment(fragment : Fragment)
    {
        val fragmentmanager = supportFragmentManager
        val fragmentTransaction = fragmentmanager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}