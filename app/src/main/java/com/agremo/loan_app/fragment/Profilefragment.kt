package com.agremo.loan_app.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.agremo.loan_app.R
import com.agremo.loan_app.activity.MainActivity
import com.agremo.loan_app.activity.splashscreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profilefragment.*


class Profilefragment : Fragment(R.layout.fragment_profilefragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signout_btn.setOnClickListener {
            var currentuser = FirebaseAuth.getInstance().currentUser

            if (currentuser != null) {
                Toast.makeText(context , "Signed out from account : ${currentuser.phoneNumber}" , Toast.LENGTH_LONG).show()
            }
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context , splashscreen::class.java )
            startActivity(intent)
            MainActivity().finish()
        }
    }
}