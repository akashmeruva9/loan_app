package com.agremo.loan_app.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.agremo.loan_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_privacy.*
import kotlinx.android.synthetic.main.fragment_myloanfragment.*

class privacy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        var progresdialog = ProgressDialog(this)
        progresdialog.setTitle("Loading Data!!")
        progresdialog.show()

        val db = FirebaseDatabase.getInstance()
        val refrence = db.reference.child("AdminBankData")


        refrence.child("privacypolicy").get().addOnSuccessListener {

               pptv2.text  = "${it.value}"

            progresdialog.dismiss()
            }.addOnFailureListener{

                Toast.makeText(this , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }




    }
}