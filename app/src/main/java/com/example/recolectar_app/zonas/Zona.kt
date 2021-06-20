package com.example.recolectar_app.zonas

import com.example.recolectar_app.contenedores.Contenedor
import java.io.Serializable


data class Zona(var id: String) : Serializable {
    val type : String = "Zona"
    var refVehicle: RefVehicle? = null
    lateinit var contenedores : Contenedores


    data class RefVehicle(var value : String){
        val type: String = "Relationship"
    }

    data class Contenedores(val value : ArrayList<Contenedor>){
        val type: String = "List"

        fun addContenedor(contenedor : Contenedor){
            value.add(contenedor)
        }
    }

    fun setContenedores(arr : ArrayList<Contenedor>){
        this.contenedores = Contenedores(arr)
    }


    fun setRefVehicleValue(string: String){
        this.refVehicle = RefVehicle("vehicle:${string}")
    }

    init {
        this.id = "zona:${id}"
    }



}

