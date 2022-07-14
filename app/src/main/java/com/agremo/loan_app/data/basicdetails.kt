package com.agremo.loan_app.data

import android.os.Parcel
import android.os.Parcelable

class basicdetails{


    var name : String? = null
    var dateofbirth : String? = null
    var  gender : String? = null
    var emailadress : String? = null
    var streetadress : String? = null
    var state : String? = null
    var district : String? = null
    var city : String? = null
    var pincode : String? = null

    constructor(
        name: String?,
        dateofbirth: String?,
        gender: String?,
        emailadress: String?,
        streetadress: String?,
        state: String?,
        district: String?,
        city: String?,
        pincode: String?
    ) {
        this.name = name
        this.dateofbirth = dateofbirth
        this.gender = gender
        this.emailadress = emailadress
        this.streetadress = streetadress
        this.state = state
        this.district = district
        this.city = city
        this.pincode = pincode
    }

    constructor()
}