package com.example.recolectar_app.mapa

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class Instruccion {
    var instruccion : String = ""
    var accion : String =""
    var inicio : LatLng = LatLng(0.0,0.0)
    var fin : LatLng = LatLng(0.0,0.0)
    var inclinacionMapa =0.0

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
    fun calculoInclinacion(): Double {
        val latitude1 = Math.toRadians(inicio.latitude)
        val latitude2 = Math.toRadians(fin.latitude)
        val longDiff = Math.toRadians(fin.longitude - inicio.longitude)
        val y = sin(longDiff) * cos(latitude2)
        val x =
            cos(latitude1) * sin(latitude2) - sin(
                latitude1
            ) * cos(latitude2) * cos(longDiff)
        return (Math.toDegrees(atan2(y, x)) + 360.0) % 360.0
    }
}