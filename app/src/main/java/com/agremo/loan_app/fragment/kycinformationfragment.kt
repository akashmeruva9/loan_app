package com.agremo.loan_app.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.agremo.loan_app.R
import com.agremo.loan_app.data.kycdetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_kycinformationfragment.*
import java.util.*

class kycinformationfragment : Fragment(R.layout.fragment_kycinformationfragment) {

    var aadharfront  = "empty"
    var aadharback  = "empty"
    var pancardfront  = "empty"
    var pancardback  = "empty"
    var aadharfrontlink  = false
    var aadharbacklink  = false
    var pancardfrontlink  = false
    var pancardbacklink  = false
    var strrefrence : StorageReference? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val date = c.get(Calendar.DAY_OF_MONTH)
        val dateofapplication = "$date/${month+1}/$year"


        val db = FirebaseDatabase.getInstance()
        val refrence = db.reference.child("Users")

        strrefrence = FirebaseStorage.getInstance().reference


        kyc_continue_btn.setOnClickListener {

            val pancardnumber = kyc_edit_1.text.toString()
            val aadharcardnumber = kyc_edit_2.text.toString()

            val data = kycdetails(pancardnumber,  aadharcardnumber)


            if (checkdata() == true) {
                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("KYCData")
                    .setValue(data).addOnCompleteListener {
                        Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT)
                            .show()
                    }

                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("ApplicationStatus")
                    .setValue("Applied")

                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("AppliedDate").setValue(dateofapplication)

                Navigation.findNavController(view).navigate(R.id.action_kycinformationfragment_to_myloanfragment)

            } else {
                Toast.makeText(context, "Empty or Invalid Data", Toast.LENGTH_LONG).show()
            }
        }

        pan_card_img1.setOnClickListener {
            selectimage( 100 )
        }

        pan_card_img2.setOnClickListener {
            selectimage( 101 )
        }

        aadhar_card_img1.setOnClickListener {
            selectimage( 102 )
        }

        aadhar_card_img2.setOnClickListener {
            selectimage( 103 )
        }

    }

    private fun checkdata(): Boolean  {

        var a = true

        if(kyc_edit_1.text.isNullOrEmpty())
        {
            kyc_edit_1.error = "required"
            a = false
        }else if(kyc_edit_1.text.length != 10)
        {
            kyc_edit_1.error = "pan card number should be 10 words"
            a = false
        }

        if(kyc_edit_2.text.isNullOrEmpty())
        {
            kyc_edit_2.error = "required"
            a = false
        }else if(kyc_edit_2.text.length != 12)
        {
            kyc_edit_2.error = "aadhar number should be 12 numbers"
            a = false
        }

        if(aadharbacklink == false )
        {
            Toast.makeText(context , "Please upload Aadhar card images" , Toast.LENGTH_SHORT).show()
            a = false
        }

        if(aadharfrontlink == false )
        {
            Toast.makeText(context , "Please upload Aadhar card images" , Toast.LENGTH_SHORT).show()
            a = false
        }

        if(pancardfrontlink == false )
        {
            Toast.makeText(context , "Please upload Pan card images" , Toast.LENGTH_SHORT).show()
            a = false
        }

        if(pancardbacklink == false)
        {
            Toast.makeText(context , "Please upload Pan card images" , Toast.LENGTH_SHORT).show()
            a = false
        }

        return a
    }



    private fun selectimage(i: Int) {

        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent , i)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == 100 && data != null )
        {

            pancardfront = data.dataString.toString()

            pan_card_img1.setImageURI(pancardfront.toUri())
            uploadimage(100)
        }

        if(requestCode == 101 && data != null )
        {

            pancardback = data.dataString.toString()

            pan_card_img2.setImageURI(pancardback.toUri())

            uploadimage(101)
        }

        if(requestCode == 102 && data != null )
        {

            aadharfront = data.dataString.toString()

            aadhar_card_img1.setImageURI(aadharfront.toUri())

            uploadimage(102)
        }

          if(requestCode == 103 && data != null )
        {

            aadharback = data.dataString.toString()

            aadhar_card_img2.setImageURI(aadharback.toUri())

            uploadimage(103)
        }
    }

    private fun uploadimage(i: Int) {

        val progresdialog = ProgressDialog(context)
        progresdialog.setTitle("Uploading Image")
        progresdialog.show()

        if (i == 100) {

            strrefrence = FirebaseStorage.getInstance().getReference("images/${FirebaseAuth.getInstance().currentUser?.phoneNumber.toString()}PanCardFront")
            strrefrence?.putFile(pancardfront.toUri())?.addOnCompleteListener {
                Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
                pancardfrontlink = true
                if(progresdialog.isShowing)
                    progresdialog.dismiss()
            }?.addOnFailureListener {
                Toast.makeText(context, "${it}", Toast.LENGTH_SHORT).show()
                if(progresdialog.isShowing)
                    progresdialog.dismiss()
            }

        }

        if (i == 101) {

            strrefrence = FirebaseStorage.getInstance().getReference("images/${FirebaseAuth.getInstance().currentUser?.phoneNumber.toString()}PanCardBack")
            strrefrence?.putFile(pancardback.toUri())?.addOnCompleteListener {
                Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
                pancardbacklink = true
                if(progresdialog.isShowing)
                    progresdialog.dismiss()


            }?.addOnFailureListener {
                Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show()
                if(progresdialog.isShowing)
                    progresdialog.dismiss()
            }

        }

        if (i == 102) {

            strrefrence = FirebaseStorage.getInstance().getReference("images/${FirebaseAuth.getInstance().currentUser?.phoneNumber.toString()}AadharCardFront")
            strrefrence?.putFile(aadharfront.toUri())?.addOnCompleteListener {
                Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
                aadharfrontlink = true
                if(progresdialog.isShowing)
                    progresdialog.dismiss()
            }?.addOnFailureListener {
                Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show()
                if(progresdialog.isShowing)
                    progresdialog.dismiss()
            }

        }

        if (i == 103) {

            strrefrence = FirebaseStorage.getInstance().getReference("images/${FirebaseAuth.getInstance().currentUser?.phoneNumber.toString()}AadharCardBack")
            strrefrence?.putFile(aadharback.toUri())?.addOnCompleteListener {
                Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
                aadharbacklink = true
                if(progresdialog.isShowing)
                    progresdialog.dismiss()
            }?.addOnFailureListener {
                Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show()
                if(progresdialog.isShowing)
                    progresdialog.dismiss()
            }

        }

    }
}