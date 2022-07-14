package com.agremo.loan_app.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.Window
import android.widget.Toast
import com.agremo.loan_app.R
import com.agremo.loan_app.activity.detail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.dialog_loanrepayment.*
import kotlinx.android.synthetic.main.dialog_loantracker.*
import kotlinx.android.synthetic.main.dialog_notifications.*
import kotlinx.android.synthetic.main.fragment_homefragment.*


class homefragment : Fragment(R.layout.fragment_homefragment) {

    val db = FirebaseDatabase.getInstance()
    val refrence = db.reference.child("Users")
    lateinit var progresdialog : ProgressDialog

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progresdialog = ProgressDialog(requireContext())
        progresdialog.setTitle("Loading Data!!")
        progresdialog.show()

        cv1.setOnClickListener {
            val intent = Intent(context , detail::class.java)
            startActivity(intent)
        }


        cv5.setOnClickListener {

            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_loanrepayment)


            dialog.close_drp.setOnClickListener {
                dialog.dismiss()
            }

            refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                .child("ApplicationStatus").get().addOnSuccessListener {

                    val a  = "${it.value}"

                    if( a == "Applied")
                        dialog.drp_tv2.text = "Loan in Progress"
                    else if( a == "Granted")
                        dialog.drp_tv2.text = "Loan Granted"
                    else
                        dialog.drp_tv2.text = "Not applied for loan"

                    if(a == "Granted")
                    {
                        dialog.drp_img1.setImageResource(R.drawable.customercare)

                        refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                            .child("LoanData").child("emi").get().addOnSuccessListener {

                                dialog.drp_tv3.visibility = View.VISIBLE

                                dialog.drp_tv3.text = "Your Monthly Emi is ₹${it.value} \n Please Contact Customer Care \n 1800 123 465"

                            }

                    }else
                    {
                        dialog.drp_img1.setImageResource(R.drawable.statusapplies)
                    }

                }.addOnFailureListener{

                    Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
                }

            dialog.show()
        }


        cv3.setOnClickListener {


            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_notifications)

            dialog.close_dn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }


        cv6.setOnClickListener {

            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_loantracker)


            dialog.close_dlt.setOnClickListener {
                dialog.dismiss()
            }

            refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                .child("ApplicationStatus").get().addOnSuccessListener {

                    val a  = "${it.value}"

                    if( a == "Applied")
                        dialog.dlt_tv2.text = "Loan in Progress"
                    else if( a == "Granted")
                        dialog.dlt_tv2.text = "Loan Granted"
                    else
                        dialog.dlt_tv2.text = "Not applied for loan"

                    if(a == "Granted")
                    {
                        dialog.dlt_img1.setImageResource(R.drawable.statusapproved)
                    }else
                    {
                        dialog.dlt_img1.setImageResource(R.drawable.statusapplies)
                    }

                }.addOnFailureListener{

                    Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
                }

            dialog.show()
        }
        clearFindViewByIdCache()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val basic = refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
            .child("BasicData")

        if(basic.toString() != "null") {

            refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                .child("BasicData").child("name").get().addOnSuccessListener {

                    settext(it.value.toString())

                    progresdialog.dismiss()

                }.addOnFailureListener {

                    Toast.makeText(context, "Fetching Data Failed !", Toast.LENGTH_SHORT).show()
                }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun settext(text: String) {

        if( text != null && text != "null")
        home_tv1789.text = "Hello $text"
    }
}