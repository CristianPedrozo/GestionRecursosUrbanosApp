package com.example.recolectar_app.zonas

import android.widget.Toast

data class Zona(val id: String, val refVehicle: RefVehicle) {
    val type : String = "Zona"

    data class RefVehicle(var value : String){
        val type: String = "Relationship"
    }

    init {
        this.refVehicle.value = "vehicle:${this.refVehicle.value}"
    }
}

