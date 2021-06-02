package com.example.recolectar_app.entities

import android.os.Parcel
import android.os.Parcelable

class Camion(patente: String?, tipo: String?):Parcelable {
    var patente: String = ""

    var tipo: String = ""


    init {
        this.patente = patente!!
        this.tipo = tipo!!
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }
}
