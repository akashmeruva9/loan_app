package com.agremo.loan_app.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.agremo.loan_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_loansplash.*


class loansplash : Fragment(R.layout.fragment_loansplash) {

    var progressView: ViewGroup? = null
    protected var isProgressShowing = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        showProgressingView()

        val db = FirebaseDatabase.getInstance()
        val refrence = db.reference.child("Users")
        var status: String = "empty"



        refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString()).child("ApplicationStatus")
            .get().addOnSuccessListener {
                status = "${it.value}"

                if(status == "null" || status == "start")
                {
                    Navigation.findNavController(view).navigate(R.id.action_loansplash2_to_loanamountfragment)
                }else if (status == "Applied" || status == "Granted")
                {
                    Navigation.findNavController(view).navigate(R.id.action_loansplash2_to_myloanfragment)
                }

                hideProgressingView()

            }.addOnFailureListener{

                Toast.makeText(context , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }



        loansplash_continue_btn.setOnClickListener {

            if(status != null || status != "start") {

                if(status == "loandetailsfilled")
                    Navigation.findNavController(view).navigate(R.id.action_loansplash2_to_bankinfofragment)
                else if(status == "bankdetailsfilled")
                    Navigation.findNavController(view).navigate(R.id.action_loansplash2_to_basicinfofragment)
                else if(status == "basicdetailsfilled")
                    Navigation.findNavController(view).navigate(R.id.action_loansplash2_to_kycinformationfragment)
                else if(status == "Applied")
                    Navigation.findNavController(view).navigate(R.id.action_loansplash2_to_myloanfragment)
            }

        }

            loan_start_btn.setOnClickListener{

                if(status != null || status != "start") {
                    refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                        .removeValue()
                    Navigation.findNavController(view)
                        .navigate(R.id.action_loansplash2_to_loanamountfragment)
                }
            }


    }

    fun showProgressingView() {
        if (!isProgressShowing) {
            isProgressShowing = true
            progressView = layoutInflater.inflate(R.layout.progressbar_layout, null) as ViewGroup
            val v: View = loading_pg_bar123.rootView
            val viewGroup = v as ViewGroup
            viewGroup.addView(progressView)
        }
    }

    fun hideProgressingView() {
        val v: View = loading_pg_bar123.rootView
        val viewGroup = v as ViewGroup
        viewGroup.removeView(progressView)
        isProgressShowing = false
    }

}