package com.agremo.loan_app.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.agremo.loan_app.R
import com.agremo.loan_app.activity.repayment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_mywallet.*


class mywalletfragment : Fragment(R.layout.fragment_mywallet){

    val db = FirebaseDatabase.getInstance()
    val refrence = db.reference.child("Users")
    lateinit var progresdialog : ProgressDialog

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progresdialog = ProgressDialog(requireContext())
        progresdialog.setTitle("Loading Data!!")
        progresdialog.show()

        var date : String? = null


            refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
            .child("AppliedDate").get().addOnSuccessListener {

                 date = it.value.toString()
            }


        refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
            .child("ApplicationStatus").get().addOnSuccessListener {

                val a  = "${it.value}"

                if( a == "Applied") {
                    mw_tv3.text = "Loan in Progress"

                    if (date != null) {
                        mw_tv4.visibility = View.VISIBLE
                        mw_tv5.text = date.toString()
                    }
                }
                else if( a == "Granted")
                {
                    if (date != null) {
                        mw_tv4.visibility = View.VISIBLE
                        mw_tv5.text = date.toString()
                    }
                    grantamount()
                    mw_tv3.text = "Loan Granted"



                    refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                        .child("Recieptstatus").get().addOnSuccessListener {

                            if(it.value.toString() == "Reciept Uploded")
                            {
                                mw_tv512.visibility = View.VISIBLE
                            }else
                            {
                                laoninsurance_btn.visibility = View.VISIBLE
                            }

                        }

                }
                else {
                    mw_tv3.text = "Not applied for loan"
                }
                progresdialog.dismiss()

            }.addOnFailureListener{

                Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }

        laoninsurance_btn.setOnClickListener {

            val intent = Intent(context , repayment::class.java)
            startActivity(intent)
        }

    }


    @SuppressLint("SetTextI18n")
    private fun grantamount() {

         clearFindViewByIdCache()

        refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
            .child("LoanData").child("loanamount").get().addOnSuccessListener {
                settext( it.value.toString())
            }.addOnFailureListener{

                Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }
    }

    @SuppressLint("SetTextI18n")
    private fun settext(text: String) {

        if( text != null || text != "null")
        mw_tv8.text = "₹$text"
        else
            mw_tv8.text = "₹0"
    }

}