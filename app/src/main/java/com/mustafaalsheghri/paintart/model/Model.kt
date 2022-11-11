package com.mustafaalsheghri.paintart.model

class Model {
    var id :String? = null
    var favStatus: String? = null
    var title:String? = null
    var imageUrl:String? = null
    var downloadUrl:String? = null





    constructor()

    constructor(id:String,favStatus: String?,title:String,image:String,dwonload:String){
        this.id = id
        this.favStatus = favStatus
        this.title = title
        this.imageUrl = image
        this.downloadUrl = dwonload

    }
}