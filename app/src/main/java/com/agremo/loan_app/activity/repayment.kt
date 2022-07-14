package com.agremo.loan_app.activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.core.net.toUri
import com.agremo.loan_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_repayment.*
import kotlinx.android.synthetic.main.dialog_after_payment.*
import kotlinx.android.synthetic.main.fragment_mywallet.*

import java.util.*

class repayment : AppCompatActivity() {

    private val db = FirebaseDatabase.getInstance()
    private val refrence = db.reference.child("Users")
    private var reciepturi = "empty"
    var reciept = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repayment)

        getdata()

        money_transfer_btn.setOnClickListener {

            if (reciept == true) {

                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("Recieptstatus").setValue("Reciept Uploded")


                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_after_payment)
                dialog.setCancelable(false)

                dialog.show()

                dialog.close_dtp.setOnClickListener {
                    dialog.dismiss()
                    var intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            } else {
                Toast.makeText(this, "Please Upload Reciept Image ", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        reciept_image.setOnClickListener {
            selectimage( 100 )
        }


    }


    private fun getdata() {

        val db123 = FirebaseDatabase.getInstance()
        val refrence123 = db123.reference

        refrence123.child("AdminBankData").child("filefees").get().addOnSuccessListener {

                ver_tv1.text = "PAYABLE AMOUNT: ₹${it.value}"
            }.addOnFailureListener{

                Toast.makeText(this , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }

        refrence123.child("AdminBankData").child("accnumber")
            .get().addOnSuccessListener {
                ver_accno.text = "${it.value}"
            }.addOnFailureListener{

                Toast.makeText(this , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
            }

        refrence123.child("AdminBankData").child("upiid").get().addOnSuccessListener {
            ver_branch.text = "${it.value}"
        }.addOnFailureListener{

            Toast.makeText(this , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
        }

        refrence123.child("AdminBankData").child("pay").get().addOnSuccessListener {
            ver_pay.text = "${it.value}"
        }.addOnFailureListener{

            Toast.makeText(this , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
        }

        refrence123.child("AdminBankData").child("ifsc").get().addOnSuccessListener {
            ver_ifsc.text = "${it.value}"
        }.addOnFailureListener{

            Toast.makeText(this, "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
        }
        refrence123.child("AdminBankData").child("bankname").get().addOnSuccessListener {
            ver_bankname.text = "${it.value}"
        }.addOnFailureListener{

            Toast.makeText(this , "Fetching Data Failed !" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectimage(i: Int) {

        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent , i)
    }

    private fun uploadimage(i: Int) {

        val progresdialog = ProgressDialog(this)
        progresdialog.setTitle("Uploading Image")
        progresdialog.show()

        if (i == 100) {

            val strrefrence = FirebaseStorage.getInstance().getReference("images/${FirebaseAuth.getInstance().currentUser?.phoneNumber.toString()}RecieptPhoto")
            strrefrence.putFile(reciepturi.toUri()).addOnCompleteListener {
                Toast.makeText(this, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
                reciept = true
                if (progresdialog.isShowing)
                    progresdialog.dismiss()
            }.addOnFailureListener {
                Toast.makeText(this, "${it}", Toast.LENGTH_SHORT).show()
                if (progresdialog.isShowing)
                    progresdialog.dismiss()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 100 && data != null) {
            reciepturi = data.dataString.toString()
            reciept_image.setImageURI(reciepturi.toUri())
            uploadimage(100)
        }
    }



}