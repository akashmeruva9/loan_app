package com.agremo.loan_app.data

class Query {

    var name : String? = null
    var emailadress : String? = null
    var message : String? = null
    var number : String? = null


    constructor(name: String?, emailadress: String?, message: String?, number: String?) {
        this.name = name
        this.emailadress = emailadress
        this.message = message
        this.number = number
    }

    constructor()
}