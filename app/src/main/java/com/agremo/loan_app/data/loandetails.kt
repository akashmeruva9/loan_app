package com.agremo.loan_app.data

import android.os.Parcel
import android.os.Parcelable

class loandetails{

    var loanamount : Int = 0
    var numberofmonths : Int = 0
    var emi : Int = 0
    var  tottalamount : Int = 0

    constructor(loanamount: Int, numberofmonths: Int, emi: Int, tottalamount: Int) {
        this.loanamount = loanamount
        this.numberofmonths = numberofmonths
        this.emi = emi
        this.tottalamount = tottalamount
    }

    constructor()
}