package com.agremo.loan_app.fragment


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.agremo.loan_app.R
import com.agremo.loan_app.data.accountdetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_bankinfofragment.*

class bankinfofragment : Fragment(R.layout.fragment_bankinfofragment){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = FirebaseDatabase.getInstance()
        val refrence = db.reference.child("Users")


        bankinfo_continue_btn.setOnClickListener {

            val accnumber = bankinfo_edittext_et1.text.toString()
            val bankname = bankinfo_edittext_et2.text.toString()
            val branchadress = bankinfo_edittext_et3.text.toString()
            val ifsccode = bankinfo_edittext_et4.text.toString()

            if(checkdata() == true ) {
                val data = accountdetails(accnumber, bankname, branchadress, ifsccode)

                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("BankData")
                    .setValue(data).addOnCompleteListener {
                        Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT)
                            .show()

                    }

                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("ApplicationStatus")
                    .setValue("bankdetailsfilled")

                Navigation.findNavController(view)
                    .navigate(R.id.action_bankinfofragment_to_basicinfofragment)
            }else
            {
                Toast.makeText(context , "Empty or Invalid Data" , Toast.LENGTH_LONG).show()
            }

        }


        clearFindViewByIdCache()
    }

    private fun checkdata(): Boolean {

        var a = true

        if(bankinfo_edittext_et1.text.isNullOrBlank())
        {
            a = false
            bankinfo_edittext_et1.error = "Required"
        }


        if(bankinfo_edittext_et2.text.isNullOrBlank())
        {
            a = false
            bankinfo_edittext_et2.error = "Required"
        }


        if(bankinfo_edittext_et3.text.isNullOrBlank())
        {
            a = false
            bankinfo_edittext_et3.error = "Required"
        }


        if(bankinfo_edittext_et4.text.isNullOrBlank())
        {
            a = false
            bankinfo_edittext_et4.error = "Required"
        }

        return a
    }
}