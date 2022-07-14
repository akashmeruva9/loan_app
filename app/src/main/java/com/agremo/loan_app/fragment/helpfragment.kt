package com.agremo.loan_app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.agremo.loan_app.R
import com.agremo.loan_app.activity.privacy
import com.agremo.loan_app.activity.terms
import com.agremo.loan_app.data.Query
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_helpfragment.*


class helpfragment : Fragment(R.layout.fragment_helpfragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = FirebaseDatabase.getInstance()
        val refrence = db.reference.child("Users")

        privacypolicy_btn.setOnClickListener {

            var intent = Intent(context , privacy()::class.java)
            startActivity(intent)
        }


    terms_btn.setOnClickListener {
        var intent = Intent(context , terms()::class.java)
        startActivity(intent)
    }



        send_btn.setOnClickListener {

            if(check_data() == true )
            {

                val name = help_et1.text.toString()
                val email = help_et2.text.toString()
                val message = help_et3.text.toString()
                val number = (FirebaseAuth.getInstance().currentUser?.phoneNumber).toString()

                val data = Query(name, email , message, number)


                val refrence11234 = db.reference
                refrence11234.child("Queries").push()
                    .setValue(data).addOnCompleteListener {
                        Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()

                        help_et1.text.clear()
                        help_et2.text.clear()
                        help_et3.text.clear()

                    }
            }
        }

        clearFindViewByIdCache()

    }

    private fun check_data(): Boolean {

        var a = true

        if(help_et1.text.toString().isNullOrEmpty())
        {
            help_et1.error = "required"
            a = false
        }

        if(help_et2.text.toString().isNullOrEmpty())
        {
            help_et2.error = "required"
            a = false
        }

        if(help_et3.text.toString().isNullOrEmpty())
        {
            help_et3.error = "required"
             a = false
        }

        return a
    }
}