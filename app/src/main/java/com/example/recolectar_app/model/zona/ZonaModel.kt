package com.example.recolectar_app.model.zona

import com.example.recolectar_app.contenedores.Contenedor
import java.io.Serializable


data class ZonaModel(var id: String) : Serializable {
    val type : String = "Zona"
    var refVehicle: RefVehicle? = null
    lateinit var nombre : Nombre
    lateinit var contenedores : Contenedores


    data class Nombre(var value : String){
        val type : String = "Text"
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

    fun setNombre(nombre : String){
        this.nombre = Nombre(nombre)
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

