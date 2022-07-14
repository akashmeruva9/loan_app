package com.agremo.loan_app.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agremo.loan_app.R
import com.agremo.loan_app.data.notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_splashscreen.*


class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val currntuser = FirebaseAuth.getInstance().currentUser

        var temp = false

        if(currntuser != null)
        {
            temp = true
        }

        spalash_getstart.setOnClickListener {

            if (temp == false) {
                val intent = Intent(this, LoginActivty::class.java)
                startActivity(intent)
                finish()
            } else {

                if (currntuser != null) {
                    Toast.makeText(this@splashscreen , "Logged in with number ${currntuser.phoneNumber}" , Toast.LENGTH_LONG).show()
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}