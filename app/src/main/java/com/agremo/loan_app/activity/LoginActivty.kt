package com.agremo.loan_app.activity

import `in`.aabhasjindal.otptextview.OTPListener
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agremo.loan_app.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import kotlinx.android.synthetic.main.activity_login_activty.*
import java.util.concurrent.TimeUnit


class LoginActivty : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var verificationId: String? = null
    lateinit var progresdialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activty)

        mAuth = FirebaseAuth.getInstance()


        getotp_btn.setOnClickListener {

            progresdialog = ProgressDialog(this)
            progresdialog.setTitle("Sending OTP")
            progresdialog.show()

            ccp.registerCarrierNumberEditText(number123)
            val phoneNumber = ccp.fullNumberWithPlus.toString().trim()


            if (phoneNumber.isNullOrEmpty()) {
                Toast.makeText(this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show()
            }else
             {
                 Toast.makeText(this, "$phoneNumber", Toast.LENGTH_SHORT).show()
                sendVerificationCode(phoneNumber.toString())
            }
        }

        clickhere_btn.setOnClickListener {

            otpcl.visibility = View.GONE
            getotp_btn.isActivated = true
            getotp_btn.isClickable = true
            getotp_btn.isEnabled = true
            getotp_btn.setBackgroundResource(R.drawable.bankinfo_btn_bac_violet)

        }

        otp_view.otpListener = object : OTPListener {
            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {

                verifyCode(otp)
            }
        }


    }
    private fun sendVerificationCode(number: String) {

        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {

        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this , "LogIn Successful" , Toast.LENGTH_LONG).show()
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {

                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private val mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)

                if(progresdialog.isShowing)
                    progresdialog.dismiss()
                verificationId = s
                otpcl.visibility = View.VISIBLE
                getotp_btn.isActivated = false
                getotp_btn.isClickable = false
                getotp_btn.isEnabled = false
                getotp_btn.setBackgroundResource(R.drawable.disabled_otnbtn)
                getotp_button.setBackgroundResource(R.drawable.disabled_otnbtn)

                clickhere_btn.isActivated = false
                clickhere_btn.isEnabled = false
                clickhere_btn.isClickable = false
                clickhere_btn.setBackgroundResource(R.drawable.disabled_otnbtn)

                start_timer()

            }


            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                if(progresdialog.isShowing)
                    progresdialog.dismiss()

                val code = phoneAuthCredential.smsCode


                if (code != null) {
                    otp_view.otp = code
                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {

                if(progresdialog.isShowing)
                    progresdialog.dismiss()

                Toast.makeText(this@LoginActivty, e.message, Toast.LENGTH_LONG).show()
            }

        }

    private fun start_timer() {
           object : CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                countdown.text = "after ${millisUntilFinished / 1000} secs"
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {

                clickhere_btn.isActivated = true
                clickhere_btn.isEnabled = true
                clickhere_btn.isClickable = true
                clickhere_btn.setBackgroundResource(R.drawable.bankinfo_btn_bac_violet)

            }
        }.start()
    }


    private fun verifyCode(code: String) {

        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)

        signInWithCredential(credential)
    }
}