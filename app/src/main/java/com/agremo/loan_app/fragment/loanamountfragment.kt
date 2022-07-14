package com.agremo.loan_app.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.agremo.loan_app.R
import com.agremo.loan_app.data.loandetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_loanamuntfragment.*


class loanamountfragment : Fragment(R.layout.fragment_loanamuntfragment) {

    private var principleamount = 10000
    private var time = 3
    private var emi = 10.0
    private var totalamount = 3390

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseDatabase.getInstance()
        val refrence = db.reference.child("Users")

        progressBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                progressBar1.max = 50
                progressBar1.min = 5
                sla_tv4.text = "₹${(progress*10000).toString().toInt()}"

                principleamount = (progress*10000).toString().toInt()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                calculate()
            }
        })

        progressBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                progressBar2.max = 36
                progressBar2.min = 3
                sla_tv8.text = "${progress.toString().toInt()} Months"

                time = progress.toString().toInt()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                calculate()
            }

        })




        loan_continue_btn.setOnClickListener {


                val data = loandetails( principleamount , time, emi.toInt(), totalamount)


                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("LoanData")
                    .setValue(data).addOnCompleteListener {
                        Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("ApplicationStatus")
                    .setValue("loandetailsfilled")

                Navigation.findNavController(view)
                    .navigate(R.id.action_loanamountfragment_to_bankinfofragment)
        }


    }


    @SuppressLint("SetTextI18n")
    private fun calculate(){

        var r = 10.0
        val t = time.toDouble()
        val p = principleamount.toDouble()

        r /= (12 * 100)


        emi = ((p * r * Math.pow((1 + r), t).toFloat()
                / (Math.pow((1 + r), t) - 1).toFloat()))

         emi_text.text = emi.toInt().toString()

        totalamount = (emi*time).toInt()

        totalamount_text.text = totalamount.toString()

    }
}