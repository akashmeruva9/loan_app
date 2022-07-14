package com.agremo.loan_app.data

import android.os.Parcel
import android.os.Parcelable

class kycdetails{


    var pancardnumber : String? = null
    var aadhaarnumber : String? = null


    constructor()
    constructor(
        pancardnumber: String?,
        aadhaarnumber: String?
    ) {
        this.pancardnumber = pancardnumber
        this.aadhaarnumber = aadhaarnumber
    }

}