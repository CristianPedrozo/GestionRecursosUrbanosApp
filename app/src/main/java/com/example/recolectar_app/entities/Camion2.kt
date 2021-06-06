package com.example.recolectar_app.entities

import android.os.Parcel
import android.os.Parcelable
//No usar esta clase, se usó para pruebas, la clase a utilizar está en Camiones>Camion

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


/*
data class Camion2(
    var id: String,
) {
    val type : String = "Vehicle"
    init {
        this.id = "vehicle:${id}"
    }
}

 */