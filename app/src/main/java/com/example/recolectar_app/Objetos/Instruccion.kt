package com.example.recolectar_app.Objetos

import android.location.Location
import com.google.android.gms.maps.model.LatLng

class Instruccion {
    var instruccion : String = ""
    var accion : String =""
    var inicio : LatLng = LatLng(0.0,0.0)
    var fin : LatLng = LatLng(0.0,0.0)

    fun estoyCerca(ubicacion:LatLng): Boolean {
        val locationA = Location("punto A")
        val locationB = Location("punto B")
        locationA.latitude=fin.latitude
        locationA.longitude=fin.longitude

        locationB.latitude=ubicacion.latitude
        locationB.longitude=ubicacion.longitude

        if(locationA.distanceTo(locationB)<10.0){
            return true
        }
        return false
    }

}