package com.mustafaalsheghri.paintart.model

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class FirebaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //enable offline data on firebase database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}