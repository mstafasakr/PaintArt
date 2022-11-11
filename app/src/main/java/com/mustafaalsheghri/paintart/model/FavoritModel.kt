package com.mustafaalsheghri.paintart.model

class FavoritModel {
    var id:String? = null
    var imageUrl:String? = null


    constructor()

    constructor(id:String,image:String){
        this.id = id
        this.imageUrl = image

    }
}