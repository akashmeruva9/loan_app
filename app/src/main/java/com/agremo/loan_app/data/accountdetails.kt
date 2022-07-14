package com.agremo.loan_app.data

import android.os.Parcel
import android.os.Parcelable

class accountdetails{

    var accountnumber : String? = null
    var bankname : String? = null
    var branchadress : String? = null
    var ifsccode : String? = null

    constructor(
        accountnumber: String?,
        bankname: String?,
        branchadress: String?,
        ifsccode: String?
    ) {
        this.accountnumber = accountnumber
        this.bankname = bankname
        this.branchadress = branchadress
        this.ifsccode = ifsccode
    }

    constructor()
}