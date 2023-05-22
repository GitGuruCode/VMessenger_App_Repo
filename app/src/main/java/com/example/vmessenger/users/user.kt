package com.example.vmessenger.users

import android.media.Image

class user {
    var name:String?=null
    var email:String?=null
    var uid:String?=null

    var userName:String?=null
    var image:String?=null
    var message:String?=null
    constructor(){}
    constructor( name:String,email:String,uid:String){
        this.name=name
        this.email=email
        this.uid=uid
    }
    constructor(image: String,userName:String){
        this.image=image
        this.userName=userName
    }

}