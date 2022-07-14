package com.agremo.loan_app.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.agremo.loan_app.R
import com.agremo.loan_app.activity.MainActivity
import com.agremo.loan_app.activity.detail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_myloanfragment.*


class myloanfragment : Fragment(R.layout.fragment_myloanfragment) {


    val db = FirebaseDatabase.getInstance()
    val refrence = db.reference.child("Users")
    lateinit var progresdialog : ProgressDialog

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progresdialog = ProgressDialog(requireContext())
        progresdialog.setTitle("Loading Data!!")
        progresdialog.show()

        getdata()

        myloan_walletbtn.setOnClickListener {

            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            detail().finish()
        }

    }

    private fun getdata() {


        refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
            .child("AppliedDate").get().addOnSuccessListener {

                myloan_tv8.text  = "${it.value}"

            }.addOnFailureListener{

                Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }

        refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
            .child("BasicData").child("name").get().addOnSuccessListener {
                myloan_tv1.text = "Hello ${it.value}"
            }.addOnFailureListener{

                Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }

        refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
            .child("LoanData").child("loanamount").get().addOnSuccessListener {
                myloan_tv6.text  = "₹${it.value}"
            }.addOnFailureListener{

                Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }


        refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
            .child("LoanData").child("numberofmonths").get().addOnSuccessListener {
                myloan_tv10.text  = "${it.value} Months"
            }.addOnFailureListener{

                Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }

        refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
            .child("ApplicationStatus").get().addOnSuccessListener {

                var a = it.value.toString()

                if(a == "Granted")
                {
                    statusimg.setImageResource(R.drawable.statusapproved)
                    statusbox.setBackgroundColor(Color.GREEN)
                    myloan_tv13.text = "Your loan application has been approved kindly transfer the money from wallet to Bank account"
                    myloan_tv12.text  = a
                }else if( a == "Applied")
                {
                    myloan_tv12.text  = "Pending...."
                    statusimg.setImageResource(R.drawable.statusapplies)
                    statusbox.setBackgroundColor(Color.RED)

                }





                progresdialog.dismiss()
            }.addOnFailureListener{

                Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }
    }
}