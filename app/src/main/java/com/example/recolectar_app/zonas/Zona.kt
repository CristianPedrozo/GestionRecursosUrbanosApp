package com.example.recolectar_app.zonas

import android.os.Parcel
import android.os.Parcelable
import com.example.recolectar_app.contenedores.Contenedor


data class Zona(var id: String?) : Parcelable {
    val type : String = "Zona"
    var refVehicle: RefVehicle? = null
    var contenedores : Contenedores = Contenedores(ArrayList())

    constructor(parcel: Parcel) : this(parcel.readString()) {

    }

    data class RefVehicle(var value : String){
        val type: String = "Relationship"
    }

    data class Contenedores(val value : ArrayList<Contenedor>){
        val type: String = "List"

        fun addContenedor(contenedor : Contenedor){
            value.add(contenedor)
        }
    }


    fun setRefVehicleValue(string: String){
        this.refVehicle = RefVehicle("vehicle:${string}")
    }

    init {
        this.id = "zona:${id}"
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Zona> {
        override fun createFromParcel(parcel: Parcel): Zona {
            return Zona(parcel)
        }

        override fun newArray(size: Int): Array<Zona?> {
            return arrayOfNulls(size)
        }
    }


}

